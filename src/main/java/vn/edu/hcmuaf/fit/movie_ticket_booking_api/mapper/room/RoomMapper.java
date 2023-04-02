package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.room;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.BranchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.RoomDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat.SeatDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.object_key.BaseObjectMapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper extends BaseObjectMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Named("toSeatDtoWithoutRoom")
    @Mapping(target = "room", ignore = true)
    SeatDto toSeatDtoWithoutRoom(Seat seat);

    @Named("toBranchDtoWithoutRooms")
    @Mapping(target = "rooms", ignore = true)
    BranchDto toBranchDtoWithoutRooms(Branch branch);

    @Named("toRoomDto")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "toSeatDtoWithoutRoom")
    @Mapping(target = "branch", source = "branch", qualifiedByName = "toBranchDtoWithoutRooms")
    RoomDto toRoomDto(final Room room);

    Room toRoom(final RoomDto roomDto);

    @IterableMapping(qualifiedByName = "toRoomDto")
    List<RoomDto> toRoomDtoList(final List<Room> roomList);

    List<Room> toRoomList(final List<RoomDto> roomDtoList);
}
