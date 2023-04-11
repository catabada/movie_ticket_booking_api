package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat.SeatDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Seat;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SeatMapper {

    @Named("toSeatDto")
    SeatDto toSeatDto(final Seat seat);

    @Named("toSeatDtoWithoutRoom")
    @Mapping(target = "room", ignore = true)
    SeatDto toSeatDtoWithoutRoom(final Seat seat);

    Seat toSeat(final SeatDto seatDto);

    @IterableMapping(qualifiedByName = "toSeatDto")
    List<SeatDto> toSeatDtoList(final List<Seat> seatList);

    List<Seat> toSeatList(final List<SeatDto> seatDtoList);
}
