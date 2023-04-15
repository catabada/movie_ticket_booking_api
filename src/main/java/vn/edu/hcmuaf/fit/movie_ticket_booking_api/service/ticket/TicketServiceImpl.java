package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.TicketMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.seat.SeatCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ticket.TicketCustomRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
    private final TicketCustomRepository ticketCustomRepository;
    private final SeatCustomRepository seatCustomRepository;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketServiceImpl(TicketCustomRepository ticketCustomRepository, SeatCustomRepository seatCustomRepository, TicketMapper ticketMapper) {
        this.ticketCustomRepository = ticketCustomRepository;
        this.seatCustomRepository = seatCustomRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public List<TicketDto> searchTicket(TicketSearch ticketSearch) throws Exception {
        return ticketMapper.toTicketDtoList(ticketCustomRepository.searchTicket(ticketSearch));
    }

    @Override
    public TicketInfoDto getTicketInfo(Long id) throws Exception {
        Optional<Ticket> ticket = ticketCustomRepository.findById(id);

        if (ticket.isEmpty()) {
            throw new BadRequestException("Ticket not found");
        }

        return ticketMapper.toTicketInfoDto(ticket.get());
    }

    @Override
    @Transactional
    public TicketDto bookingTicket(TicketCreate ticketCreate) throws Exception {
        Ticket ticket = ticketMapper.toTicket(ticketCreate);

        Seat seat = seatCustomRepository.findById(ticketCreate.getSeat().getId())
                .orElseThrow(() -> new BadRequestException("Seat not found"));
        ticket.setSeat(seat);

        Ticket newTicket = ticketCustomRepository.save(ticket);
        return ticketMapper.toTicketDto(newTicket);
    }

    @Override
    public TicketDto updateTicket(TicketUpdate ticketUpdate) throws Exception {
        return null;
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) throws Exception {

    }
}
