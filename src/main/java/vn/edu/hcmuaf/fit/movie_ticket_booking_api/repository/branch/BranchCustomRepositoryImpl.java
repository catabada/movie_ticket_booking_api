package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.branch;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.BranchSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Branch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QBranch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QRoom;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class BranchCustomRepositoryImpl extends AbstractCustomRepository<Branch, Long> implements BranchCustomRepository {
    private final QBranch qBranch = QBranch.branch;
    private final QRoom qRoom = QRoom.room;

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

    @Override
    public List<Branch> getBranchSearch(BranchSearch search) {
        BooleanBuilder booleanBuilder = buildCondition(search);
        return queryFactory
                .selectFrom(qBranch)
                .innerJoin(qBranch.rooms, qRoom)
                .where(booleanBuilder.and(qRoom.state.ne(ObjectState.DELETED)))
                .fetch();
    }

    private BooleanBuilder buildCondition(BranchSearch search) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (!ObjectUtils.isEmpty(search.isHasRoom())) {
            if (search.isHasRoom())
                booleanBuilder.and(qBranch.rooms.isNotEmpty());
        }

        return booleanBuilder.and(qBranch.state.ne(ObjectState.DELETED));

    }
}
