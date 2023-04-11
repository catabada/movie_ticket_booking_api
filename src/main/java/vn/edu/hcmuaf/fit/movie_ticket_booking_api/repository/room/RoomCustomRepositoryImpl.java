package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.room;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Room;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QRoom;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.Optional;

@Repository
public class RoomCustomRepositoryImpl extends AbstractCustomRepository<Room, Long> implements RoomCustomRepository {
    private final QRoom qRoom = QRoom.room;

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
}
