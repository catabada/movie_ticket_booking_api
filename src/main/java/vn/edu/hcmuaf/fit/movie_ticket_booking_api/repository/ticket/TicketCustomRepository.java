package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ticket;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Ticket;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.List;

public interface TicketCustomRepository extends ICustomRepository<Ticket, Long> {
    List<Ticket> searchTicket(TicketSearch ticketSearch);
}
