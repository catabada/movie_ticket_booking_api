package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.branch;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.BranchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Branch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.object_key.BaseObjectMapper;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BranchMapper extends BaseObjectMapper {
    BranchDto toBranchDto(final Branch branch);

    Branch toBranch(final BranchDto branchDto);

    List<BranchDto> toBranchDtoList(final List<Branch> branchList);

    List<Branch> toBranchList(final List<BranchDto> branchDtoList);
}
