package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.checkout;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.NoticeType;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.PaymentStatus;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoleConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice.NoticeDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment.CaptureMoMoConfirmResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment.MomoResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat.SeatDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_user.AppUserCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.invoice.InvoiceRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.product.ProductCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.seat.SeatCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.showtime.ShowtimeCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ticket.TicketCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_mail.AppMailService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.notice.NoticeService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.payment.PaymentService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {
    private final AppUserCustomRepository appUserCustomRepository;
    private final InvoiceRepository invoiceRepository;
    private final ShowtimeCustomRepository showtimeCustomRepository;
    private final SeatCustomRepository seatCustomRepository;
    private final TicketCustomRepository ticketCustomRepository;
    private final ProductCustomRepository productCustomRepository;
    private final PaymentService paymentService;
    private final AppUserMapper appUserMapper;
    private final InvoiceMapper invoiceMapper;
    private final ShowtimeMapper showtimeMapper;
    private final TicketMapper ticketMapper;
    private final AppMailService appMailService;
    private final InvoiceComboMapper invoiceComboMapper;
    private final NoticeService noticeService;
    private final NoticeMapper noticeMapper;

    @Autowired
    public CheckoutServiceImpl(AppUserCustomRepository appUserCustomRepository, InvoiceRepository invoiceRepository,
                               ShowtimeCustomRepository showtimeCustomRepository, SeatCustomRepository seatCustomRepository,
                               TicketCustomRepository ticketCustomRepository, PaymentService paymentService,
                               AppUserMapper appUserMapper, InvoiceMapper invoiceMapper,
                               ShowtimeMapper showtimeMapper, TicketMapper ticketMapper,
                               AppMailService appMailService,
                               InvoiceComboMapper invoiceComboMapper,
                               NoticeService noticeService,
                               ProductCustomRepository productCustomRepository,
                               NoticeMapper noticeMapper
    ) {
        this.appUserCustomRepository = appUserCustomRepository;
        this.invoiceRepository = invoiceRepository;
        this.productCustomRepository = productCustomRepository;
        this.showtimeCustomRepository = showtimeCustomRepository;
        this.seatCustomRepository = seatCustomRepository;
        this.ticketCustomRepository = ticketCustomRepository;
        this.paymentService = paymentService;
        this.appUserMapper = appUserMapper;
        this.invoiceMapper = invoiceMapper;
        this.showtimeMapper = showtimeMapper;
        this.ticketMapper = ticketMapper;
        this.appMailService = appMailService;
        this.invoiceComboMapper = invoiceComboMapper;
        this.noticeService = noticeService;
        this.noticeMapper = noticeMapper;
    }

    private InvoiceDto checkout(InvoiceCreate invoiceCreate) throws Exception {
        Invoice invoice = Invoice.builder()
                .id(0L)
                .name(invoiceCreate.getName())
                .totalPrice(invoiceCreate.getTotalPrice())
                .email(invoiceCreate.getEmail())
                .paymentMethod(invoiceCreate.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .invoiceCombos(invoiceComboMapper.toInvoiceComboList(invoiceCreate.getInvoiceCombos()))
                .state(ObjectState.ACTIVE)
                .build();

        Showtime showtime = showtimeCustomRepository.findById(invoiceCreate.getShowtime().getId())
                .orElseThrow(() -> new BadRequestException("Không tìm thấy suất chiếu"));
        invoice.setShowtime(showtime);

        List<Seat> seats = new ArrayList<>();
        for (SeatDto seatDto : invoiceCreate.getSeats()) {
            Seat seat = seatCustomRepository.findById(seatDto.getId())
                    .orElseThrow(() -> new BadRequestException("Không tìm thấy ghế có mã " + seatDto.getCode()));
            seats.add(seat);
        }
        List<Ticket> tickets = createTicketList(seats);

        String currentUserEmail = AppUtils.getCurrentEmail();
        if (!currentUserEmail.isBlank()) {
            if (!invoiceCreate.getEmail().equals(currentUserEmail)) {
                throw new BadRequestException("Email không trùng khớp");
            }

            AppUser appUser = appUserCustomRepository.getUserByEmail(currentUserEmail)
                    .orElseThrow(() -> new BadRequestException("Không tìm thấy người dùng"));

            if (appUser.getAppRoles().stream().noneMatch(role -> role.getName().equals(RoleConstant.ROLE_MEMBER))) {
                throw new BadRequestException("Bạn không có quyền thanh toán");
            }

            invoice.setAppUser(appUser);
        }

        for (Ticket ticket : tickets) {
            ticket.setInvoice(invoice);
        }

        for (InvoiceCombo invoiceCombo : invoice.getInvoiceCombos()) {
            for (ComboItem comboItem : invoiceCombo.getCombo().getComboItems()) {
                if (comboItem.getQuantity() * invoiceCombo.getQuantity() > comboItem.getProduct().getStock()) {
                    throw new BadRequestException("Số lượng sản phẩm trong combo" + invoiceCombo.getCombo().getName() + " không đủ");
                }
            }

            invoiceCombo.setInvoice(invoice);
        }

        invoice.setCode(AppUtils.createInvoiceCode());
        invoice.setTickets(tickets);

        invoice.setInvoiceCombos(invoice.getInvoiceCombos());

        invoice = invoiceRepository.saveAndFlush(invoice);

        return invoiceMapper.toInvoiceDto(invoice);
    }

    @Override
    public MomoResponse checkoutMomo(InvoiceCreate invoiceCreate) throws Exception {
        return paymentService.createMomoCapturePayment(checkout(invoiceCreate));
    }

    @Override
    public void returnByMomo(CaptureMoMoConfirmResponse captureMoMoConfirmResponse) throws Exception {
        Invoice invoice = invoiceRepository.findByCode(captureMoMoConfirmResponse.getOrderId())
                .orElseThrow(() -> new BadRequestException("Không tìm thấy hóa đơn"));

        InvoiceDto invoiceDto = invoiceMapper.toInvoiceDto(invoice);
        if (captureMoMoConfirmResponse.getResultCode() == 0) {
            for(InvoiceCombo invoiceCombo: invoice.getInvoiceCombos()) {
                for (ComboItem comboItem : invoiceCombo.getCombo().getComboItems()) {
                    Product product = comboItem.getProduct();
                    product.setStock(product.getStock() - comboItem.getQuantity() * invoiceCombo.getQuantity());
                    productCustomRepository.saveAndFlush(product);
                }
            }

            Optional<AppUser> optional = appUserCustomRepository.getUserByEmail(invoice.getEmail());
            if (optional.isPresent()) {
                NoticeDto notice = NoticeDto.builder()
                        .id(0L)
                        .description("Bạn đã đặt vé thành công với hóa đơn " + invoice.getCode())
                        .type(NoticeType.BOOKED)
                        .isRead(false)
                        .state(ObjectState.ACTIVE)
                        .appUser(AppUserDto.builder().email(optional.get().getEmail()).build())
                        .build();

                noticeService.createNotice(notice);
            } else {
                throw new BadRequestException("Không tìm thấy người dùng");
            }

            invoice.setPaymentStatus(PaymentStatus.SUCCESS);
            invoice.setPaymentDate(ZonedDateTime.now());
            invoiceRepository.save(invoice);

            appMailService.sendEmailBookingTicket(invoiceDto.getEmail(), invoiceDto);
        } else {
            invoice.setPaymentStatus(PaymentStatus.FAILED);
            invoiceRepository.save(invoice);
        }

    }

    @Override
    public String checkoutVNPay(InvoiceCreate invoiceCreate, HttpServletRequest request) throws Exception {
        return paymentService.createVNPayPayment(checkout(invoiceCreate), request);
    }

    @Override
    public InvoiceDto confirmBooking(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceMapper.toInvoice(invoiceDto);

        List<Ticket> tickets = ticketCustomRepository.saveAll(ticketMapper.toTicketList(invoiceDto.getTickets()));
        tickets.forEach(invoice::addTicket);

        return invoiceMapper.toInvoiceDto(invoiceRepository.save(invoice));
    }

    private List<Ticket> createTicketList(List<Seat> seats) {
        List<Ticket> tickets = new ArrayList<>();

        seats.forEach(seat -> {
            Ticket ticket = new Ticket();
            ticket.setSeat(seat);

            tickets.add(ticket);
        });


        return tickets;
    }
}
