package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.branch;

import jakarta.persistence.EntityManager;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Branch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QBranch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class BranchCustomRepositoryImpl extends AbstractCustomRepository<Branch, Long> implements BranchCustomRepository {
    private final QBranch qBranch = QBranch.branch;

    protected BranchCustomRepositoryImpl(EntityManager entityManager) {
        super(Branch.class, entityManager);
    }

    @Override
    public void saveAll() {

    }

    @Override
    @NonNull
    public List<Branch> findAll() {
        return queryFactory
                .selectFrom(qBranch)
                .orderBy(qBranch.id.asc())
                .fetch();
    }

    @Override
    public Optional<Branch> findByName(String name) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qBranch)
                .where(qBranch.name.eq(name))
                .fetchOne());
    }
}
