package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.ticket;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.*;

import java.util.List;

public interface TicketService {
    List<TicketDto> searchTicket(TicketSearch ticketSearch) throws Exception;
    TicketInfoDto getTicketInfo(Long id) throws Exception;
    TicketDto bookingTicket(TicketCreate ticketCreate) throws Exception;
    TicketDto updateTicket(TicketUpdate ticketUpdate) throws Exception;
    void deleteTicket(Long id) throws Exception;
}
