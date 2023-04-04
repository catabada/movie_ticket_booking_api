package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.ShowtimeDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Showtime;

import java.util.List;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = {RoomMapper.class, MovieGenreMapper.class, TicketMapper.class}
)
public interface ShowtimeMapper {

    @Named("toShowtimeDto")
    @Mapping(target = "room", source = "room", qualifiedByName = "toRoomDto")
    @Mapping(target = "movie", source = "movie", qualifiedByName = "toMovieDto")
    @Mapping(target = "tickets", source = "tickets", qualifiedByName = "toTicketDtoWithoutShowtime")
    ShowtimeDto toShowtimeDto(final Showtime showtime);

    Showtime toShowtime(final ShowtimeDto showtimeDto);

    @IterableMapping(qualifiedByName = "toShowtimeDto")
    List<ShowtimeDto> toShowtimeDtoList(final List<Showtime> showtimeList);

    List<Showtime> toShowtimeList(final List<ShowtimeDto> showtimeDtoList);
}
