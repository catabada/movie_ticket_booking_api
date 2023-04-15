package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketInfoDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Ticket;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {InvoiceMapper.class, SeatMapper.class}
)
public interface TicketMapper {

    @Named("toTicketInfoDto")
    @Mapping(target = "seat", source = "seat.code")
    TicketInfoDto toTicketInfoDto(final Ticket ticket);

    @Named("toTicketDto")
    @Mapping(target = "seat", source = "seat", qualifiedByName = "toSeatDtoWithoutRoom")
    TicketDto toTicketDto(final Ticket ticket);

    @Named("toTicket")
    Ticket toTicket(final TicketDto ticketDto);

    @IterableMapping(qualifiedByName = "toTicketDto")
    List<TicketDto> toTicketDtoList(final List<Ticket> ticketList);

    @IterableMapping(qualifiedByName = "toTicket")
    List<Ticket> toTicketList(final List<TicketDto> ticketDtoList);
}
