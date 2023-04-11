package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketInfoDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Ticket;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TicketMapper {

    @Named("toTicketDto")
    TicketDto toTicketDto(final Ticket ticket);

    @Named("toTicketInfoDto")
    @Mapping(target = "showtime", source = "showtime.startTime")
    @Mapping(target = "movie", source = "showtime.movie.name")
    @Mapping(target = "room", source = "showtime.room.name")
    @Mapping(target = "seat", source = "seat.code")
    TicketInfoDto toTicketInfoDto(final Ticket ticket);

    @Named("toTicketDtoWithoutShowtime")
    @Mapping(target = "showtime", ignore = true)
    TicketDto toTicketDtoWithoutShowtime(final Ticket ticket);

    Ticket toTicket(final TicketDto ticketDto);

    @IterableMapping(qualifiedByName = "toTicketDto")
    List<TicketDto> toTicketDtoList(final List<Ticket> ticketList);

    List<Ticket> toTicketList(final List<TicketDto> ticketDtoList);
}
