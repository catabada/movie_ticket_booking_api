package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.room;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.RoomSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QBranch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Room;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QRoom;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomCustomRepositoryImpl extends AbstractCustomRepository<Room, Long> implements RoomCustomRepository {
    private final QRoom qRoom = QRoom.room;
    private final QBranch qBranch = QBranch.branch;

    protected RoomCustomRepositoryImpl(EntityManager entityManager) {
        super(Room.class, entityManager);
    }

    @Override
    public void saveAll() {

    }

    @Override
    public Optional<Room> findByName(String name) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qRoom)
                .where(qRoom.name.eq(name))
                .fetchOne());
    }

    @Override
    public List<Room> getRoomsSearch(RoomSearch search) {
        BooleanBuilder booleanBuilder = buildCondition(search);
        return queryFactory
                .selectFrom(qRoom)
                .innerJoin(qRoom.branch, qBranch)
                .where(booleanBuilder.and(
                                qRoom.branch.id.eq(search.getBranch().getId()))
                        .and(qBranch.state.ne(ObjectState.DELETED))
                )
                .fetch();

    }

    private BooleanBuilder buildCondition(RoomSearch search) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(search.getBranch())) {
            if (!ObjectUtils.isEmpty(search.getBranch().getId()))
                booleanBuilder.and(qBranch.id.eq(search.getBranch().getId()));
        }
        return booleanBuilder.and(qRoom.state.ne(ObjectState.DELETED));
    }
}
