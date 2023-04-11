package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.BranchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = {RoomMapper.class}
)
public interface BranchMapper {

    @Named("toBranchDto")
    @Mapping(target = "rooms", source = "rooms", qualifiedByName = "toRoomDtoWithoutBranch")
    BranchDto toBranchDto(Branch branch);

    Branch toBranch(BranchDto branchDto);

    @IterableMapping(qualifiedByName = "toBranchDto")
    List<BranchDto> toBranchDtoList(List<Branch> branchList);

    List<Branch> toBranchList(List<BranchDto> branchDtoList);
}
