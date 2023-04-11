package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.seat;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Seat;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QSeat;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.Optional;

@Repository
public class SeatCustomRepositoryImpl extends AbstractCustomRepository<Seat, Long> implements SeatCustomRepository {
    private final QSeat qSeat = QSeat.seat;

    protected SeatCustomRepositoryImpl(EntityManager entityManager) {
        super(Seat.class, entityManager);
    }

    @Override
    public void saveAll() {

    }

    @Override
    public Optional<Seat> findByCode(String code) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qSeat)
                .where(qSeat.code.eq(code))
                .fetchOne());
    }
}
