package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.showtime;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.ShowtimeSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QShowtime;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Showtime;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ShowtimeCustomRepositoryImpl extends AbstractCustomRepository<Showtime, Long> implements ShowtimeCustomRepository {
    private final QShowtime qShowtime = QShowtime.showtime;

    protected ShowtimeCustomRepositoryImpl(EntityManager entityManager) {
        super(Showtime.class, entityManager);
    }

    @Override
    public void saveAll() {

    }

    @Override
    public List<Showtime> searchShowtime(ShowtimeSearch showtimeSearch) {
        BooleanBuilder builder = buildConditionSearch(showtimeSearch);
        return queryFactory.selectFrom(qShowtime)
                .where(builder)
                .orderBy(qShowtime.startTime.asc())
                .fetch();
    }

    @Override
    public Optional<Showtime> getShowtime(Long id) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qShowtime)
                        .where(qShowtime.id.eq(id).and(qShowtime.state.ne(ObjectState.DELETED)))
                        .fetchFirst()
        );
    }

    private BooleanBuilder buildConditionSearch(ShowtimeSearch showtimeSearch) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(showtimeSearch.getMovie())) {
            builder.and(qShowtime.movie.id.eq(showtimeSearch.getMovie().getId()));
        }
        if (!ObjectUtils.isEmpty(showtimeSearch.getBranch())) {
            builder.and(qShowtime.room.branch.id.eq(showtimeSearch.getBranch().getId()));
        }
        if (!ObjectUtils.isEmpty(showtimeSearch.getStartTime())) {
            builder.and(
                    qShowtime.startTime.after(showtimeSearch.getStartTime()).and(qShowtime.startTime.before(showtimeSearch.getStartTime().plusDays(1)))
            );
        }
        return builder.and(qShowtime.state.ne(ObjectState.DELETED));
    }
}
