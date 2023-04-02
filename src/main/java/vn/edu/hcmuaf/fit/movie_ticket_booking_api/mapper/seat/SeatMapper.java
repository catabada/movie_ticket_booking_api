package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.seat;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat.SeatDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Seat;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.object_key.BaseObjectMapper;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SeatMapper extends BaseObjectMapper {
    SeatDto toSeatDto(final Seat seat);
    Seat toSeat(final SeatDto seatDto);
    List<SeatDto> toSeatDtoList(final List<Seat> seatList);
    List<Seat> toSeatList(final List<SeatDto> seatDtoList);
}
