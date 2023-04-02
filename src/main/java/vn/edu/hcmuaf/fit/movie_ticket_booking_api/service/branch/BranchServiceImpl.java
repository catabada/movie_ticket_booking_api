package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.BranchStatus;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Branch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.branch.BranchMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.branch.BranchCustomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchCustomRepository branchCustomRepository;
    private final BranchMapper branchMapper;

    @Autowired
    public BranchServiceImpl(BranchCustomRepository branchCustomRepository, BranchMapper branchMapper) {
        this.branchCustomRepository = branchCustomRepository;
        this.branchMapper = branchMapper;
    }

    @Override
    public List<BranchDto> getAllBranches() {
        List<Branch> branchList = branchCustomRepository.findAll();
        return branchMapper.toBranchDtoList(branchList);
    }

    @Override
    public BranchDto getBranchById(Long id) throws BaseException {
        Optional<Branch> branch = branchCustomRepository.findById(id);
        return branchMapper.toBranchDtoWithRooms(branch.orElseThrow(() -> new NotFoundException("Branch not found")));
    }

    @Override
    @Transactional
    public BranchDto createBranch(BranchCreate branchCreate) throws BaseException {
//        if (branchCreate.getBranch() == null) {
//            throw new BaseException("Branch is null");
//        }

        // check if branch exists
        Optional<Branch> branch = branchCustomRepository.findByName(branchCreate.getName());

        if (branch.isPresent()) {
            throw new BadRequestException("Branch already exists");
        }

        branchCreate.setStatus(BranchStatus.ACTIVE);

        Branch newBranch = branchCustomRepository.saveAndFlush(branchMapper.toBranch(branchCreate));

        return branchMapper.toBranchDtoWithRooms(newBranch);
    }

    @Override
    public BranchDto updateBranch(BranchUpdate branchUpdate) throws BaseException {
        if (branchUpdate == null) {
            throw new BaseException("Branch is null");
        }

        Branch branch = branchMapper.toBranch(branchUpdate);

        Branch updatedBranch = branchCustomRepository.save(branch);

        return branchMapper.toBranchDto(updatedBranch);
    }

    @Override
    public void deleteBranch(Long id) throws BaseException {
        branchCustomRepository.deleteById(id);
    }
}
