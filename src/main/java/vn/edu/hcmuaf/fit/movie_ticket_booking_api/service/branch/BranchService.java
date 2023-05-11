package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.branch;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;

import java.util.List;

public interface BranchService {
    List<BranchDto> getAllBranches(final BranchSearch search);

    BranchDto getBranchById(final Long id) throws BaseException;

    BranchDto createBranch(final BranchCreate branchCreate) throws BaseException;

    BranchDto updateBranch(final BranchUpdate branchUpdate) throws BaseException;

    void deleteBranch(final Long id) throws BaseException;
}
