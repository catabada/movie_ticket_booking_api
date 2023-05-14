package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.branch;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.BranchSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Branch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BranchCustomRepository extends ICustomRepository<Branch, Long> {
    Optional<Branch> findByName(String name);
    Optional<Branch> getBranch(Long id);

    List<Branch> getBranchSearch(BranchSearch search);


}
