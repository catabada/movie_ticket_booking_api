package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.room;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.RoomDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Room;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.object_key.BaseObjectMapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper extends BaseObjectMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Named("toRoomDtoIgnoreSeat")
    @Mapping(target = "seats", ignore = true)
    RoomDto toRoomDtoIgnoreSeat(final Room room);

    @Named("toRoomDto")
    RoomDto toRoomDto(final Room room);

    Room toRoom(final RoomDto roomDto);

    List<RoomDto> toRoomDtoList(final List<Room> roomList);

    List<Room> toRoomList(final List<RoomDto> roomDtoList);
}
