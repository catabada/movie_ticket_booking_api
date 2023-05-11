package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.ShowtimeDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {RoomMapper.class, MovieGenreMapper.class, TicketMapper.class}
)
public interface ShowtimeMapper {

    @Named("toShowtimeDto")
    @Mapping(target = "room", source = "room", qualifiedByName = "toRoomDto")
    @Mapping(target = "movie", source = "movie", qualifiedByName = "toMovieDto")
    @Mapping(target = "invoices.showtime", ignore = true)
    ShowtimeDto toShowtimeDto(final Showtime showtime);

    @Named("toShowtimeDtoWithoutInvoices")
    @Mapping(target = "room", source = "room", qualifiedByName = "toRoomDto")
    @Mapping(target = "movie", source = "movie", qualifiedByName = "toMovieDto")
    @Mapping(target = "invoices", ignore = true)
    ShowtimeDto toShowtimeDtoWithoutInvoices(final Showtime showtime);

    Showtime toShowtime(final ShowtimeDto showtimeDto);

    @IterableMapping(qualifiedByName = "toShowtimeDto")
    List<ShowtimeDto> toShowtimeDtoList(final List<Showtime> showtimeList);

    List<Showtime> toShowtimeList(final List<ShowtimeDto> showtimeDtoList);

    @Mapping(target = "showtime", ignore = true)
    @Mapping(target = "tickets", source = "tickets", qualifiedByName = "toTicketDto")
    InvoiceDto toInvoiceDto(final Invoice invoice);
}
