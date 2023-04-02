package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.branch;

import jdk.jfr.Name;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.BranchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.RoomDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat.SeatDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.object_key.BaseObjectMapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BranchMapper extends BaseObjectMapper {
    BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);

    @Named("toSeatDtoWithoutRoom")
    @Mapping(target = "room", ignore = true)
    SeatDto toSeatDtoWithoutRoom(Seat seat);

    @Named("toRoomDtoWithoutBranch")
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "seats", source = "seats", qualifiedByName = "toSeatDtoWithoutRoom")
    RoomDto toRoomDtoWithoutBranch(Room room);

    @Named("toBranchDto")
    @Mapping(target = "rooms", source = "rooms", qualifiedByName = "toRoomDtoWithoutBranch")
    BranchDto toBranchDto(Branch branch);

    Branch toBranch(BranchDto branchDto);

    @IterableMapping(qualifiedByName = "toBranchDto")
    List<BranchDto> toBranchDtoList(List<Branch> branchList);

    List<Branch> toBranchList(List<BranchDto> branchDtoList);
}
