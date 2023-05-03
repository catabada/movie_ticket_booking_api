package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.checkout;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoleConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment.MomoResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat.SeatDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_user.AppUserCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.invoice.InvoiceRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.seat.SeatCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.showtime.ShowtimeCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ticket.TicketCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.payment.PaymentService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {
    private final AppUserCustomRepository appUserCustomRepository;
    private final InvoiceRepository invoiceRepository;
    private final ShowtimeCustomRepository showtimeCustomRepository;
    private final SeatCustomRepository seatCustomRepository;
    private final TicketCustomRepository ticketCustomRepository;
    private final PaymentService paymentService;

    private final AppUserMapper appUserMapper;
    private final InvoiceMapper invoiceMapper;
    private final ShowtimeMapper showtimeMapper;
    private final TicketMapper ticketMapper;

    @Autowired
    public CheckoutServiceImpl(AppUserCustomRepository appUserCustomRepository, InvoiceRepository invoiceRepository,
                               ShowtimeCustomRepository showtimeCustomRepository, SeatCustomRepository seatCustomRepository,
                               TicketCustomRepository ticketCustomRepository, PaymentService paymentService,
                               AppUserMapper appUserMapper, InvoiceMapper invoiceMapper,
                               ShowtimeMapper showtimeMapper, TicketMapper ticketMapper) {
        this.appUserCustomRepository = appUserCustomRepository;
        this.invoiceRepository = invoiceRepository;
        this.showtimeCustomRepository = showtimeCustomRepository;
        this.seatCustomRepository = seatCustomRepository;
        this.ticketCustomRepository = ticketCustomRepository;
        this.paymentService = paymentService;
        this.appUserMapper = appUserMapper;
        this.invoiceMapper = invoiceMapper;
        this.showtimeMapper = showtimeMapper;
        this.ticketMapper = ticketMapper;
    }

    private InvoiceCreate checkout(InvoiceCreate invoiceCreate) throws Exception {
        Showtime showtime = showtimeCustomRepository.findById(invoiceCreate.getShowtime().getId())
                .orElseThrow(() -> new BadRequestException("Không tìm thấy suất chiếu"));
        invoiceCreate.setShowtime(showtimeMapper.toShowtimeDto(showtime));

        List<Seat> seats = new ArrayList<>();
        for (SeatDto seatDto : invoiceCreate.getSeats()) {
            Seat seat = seatCustomRepository.findByCode(seatDto.getCode())
                    .orElseThrow(() -> new BadRequestException("Không tìm thấy ghế có mã " + seatDto.getCode()));
            seats.add(seat);
        }
        List<TicketDto> tickets = createTicketDtoList(seats);

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

            invoiceCreate.setAppUser(appUserMapper.toAppUserDtoWithoutAppRolesAndVerificationTokens(appUser));
        }

        tickets.forEach(invoiceCreate::addTicket);

        invoiceCreate.setCode(AppUtils.createInvoiceCode());

        return invoiceCreate;
    }

    @Override
    public MomoResponse checkoutMomo(InvoiceCreate invoiceCreate) throws Exception {
        return paymentService.createMomoCapturePayment(checkout(invoiceCreate));
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

    private List<TicketDto> createTicketDtoList(List<Seat> seats) {
        List<Ticket> tickets = new ArrayList<>();

	    seats.forEach(seat -> {
		    Ticket ticket = new Ticket();
		    ticket.setSeat(seat);

            tickets.add(ticket);
	    });


		return ticketMapper.toTicketDtoList(tickets);
    }
}
