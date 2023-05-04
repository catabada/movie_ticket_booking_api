package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.RoomDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {SeatMapper.class}
)
public interface RoomMapper {

    @Named("toRoomDto")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "toSeatDtoWithoutRoom")
    @Mapping(target = "branch.rooms", ignore = true)
    RoomDto toRoomDto(final Room room);

    @Named("toRoomDtoWithoutBranch")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "toSeatDtoWithoutRoom")
    RoomDto toRoomDtoWithoutBranch(final Room room);

    Room toRoom(final RoomDto roomDto);

    @IterableMapping(qualifiedByName = "toRoomDto")
    List<RoomDto> toRoomDtoList(final List<Room> roomList);

    List<Room> toRoomList(final List<RoomDto> roomDtoList);
}
