package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoleConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.*;
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

    private final AppUserMapper appUserMapper;
    private final InvoiceMapper invoiceMapper;
    private final ShowtimeMapper showtimeMapper;
    private final TicketMapper ticketMapper;

    @Autowired
    public CheckoutServiceImpl(AppUserCustomRepository appUserCustomRepository, InvoiceRepository invoiceRepository,
                               ShowtimeCustomRepository showtimeCustomRepository, SeatCustomRepository seatCustomRepository,
                               TicketCustomRepository ticketCustomRepository,
                               AppUserMapper appUserMapper, InvoiceMapper invoiceMapper,
                               ShowtimeMapper showtimeMapper, TicketMapper ticketMapper) {
        this.appUserCustomRepository = appUserCustomRepository;
        this.invoiceRepository = invoiceRepository;
        this.showtimeCustomRepository = showtimeCustomRepository;
        this.seatCustomRepository = seatCustomRepository;
        this.ticketCustomRepository = ticketCustomRepository;
        this.appUserMapper = appUserMapper;
        this.invoiceMapper = invoiceMapper;
        this.showtimeMapper = showtimeMapper;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public InvoiceDto checkout(InvoiceCreate invoiceCreate) throws BadRequestException {
	    Showtime showtime = showtimeCustomRepository.findById(invoiceCreate.getShowtime().getId())
                .orElseThrow(() -> new BadRequestException("Không tìm thấy suất chiếu"));
        invoiceCreate.setShowtime(showtimeMapper.toShowtimeDto(showtime));
        
        List<Seat> seats = seatCustomRepository.findAll(showtime, invoiceCreate.getSeatCodes());
        System.out.println(seats.size());
		if (seats.size() != invoiceCreate.getSeatCodes().size()) {
            throw new BadRequestException("Mã ghế không hợp lệ");
        }
        List<TicketDto> tickets = createTicketDtoList(seats);

        String currentUserEmail = AppUtils.getCurrentEmail();
        if (!currentUserEmail.isBlank()) {
            if (!invoiceCreate.getEmail().equals(currentUserEmail)) {
                throw new BadRequestException("Email không khớp");
            }

            AppUser appUser = appUserCustomRepository.getUserByEmail(currentUserEmail)
                    .orElseThrow(() -> new BadRequestException("Không tìm thấy người dùng"));

            if (appUser.getAppRoles().stream().noneMatch(role -> role.getName().equals(RoleConstant.ROLE_MEMBER))) {
                throw new BadRequestException("Bạn không có quyền thanh toán");
            }

            invoiceCreate.setAppUser(appUserMapper.toAppUserDtoWithoutAppRolesAndVerificationTokens(appUser));
            return invoiceCreate;
        }

        tickets.forEach(invoiceCreate::addTicket);
        return invoiceCreate;
    }

    @Override
    public InvoiceDto confirmBooking(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceMapper.toInvoice(invoiceDto);
        invoice.setCode(AppUtils.createInvoiceCode());

        List<Ticket> tickets = ticketCustomRepository.saveAllAndFlush(ticketMapper.toTicketList(invoiceDto.getTickets()));
        tickets.forEach(invoice::addTicket);

        Invoice userInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toInvoiceDto(userInvoice);
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
