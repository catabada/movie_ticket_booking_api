package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.showtime;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.ShowtimeSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QShowtime;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Showtime;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;

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
                .fetch();
    }

    private BooleanBuilder buildConditionSearch(ShowtimeSearch showtimeSearch) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(showtimeSearch.getMovie())) {
            builder.and(qShowtime.movie.name.eq(showtimeSearch.getMovie()));
        }
        if (!ObjectUtils.isEmpty(showtimeSearch.getBranch())) {
            builder.and(qShowtime.room.branch.name.eq(showtimeSearch.getBranch()));
        }
        if (!ObjectUtils.isEmpty(showtimeSearch.getStartTime())) {
            builder.and(qShowtime.startTime.eq(showtimeSearch.getStartTime()));
        }
        return builder;
    }
}
