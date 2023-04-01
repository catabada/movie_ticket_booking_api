package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.genre;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Genre;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QGenre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreCustomRepositoryImpl extends AbstractCustomRepository<Genre, BaseObject> implements GenreCustomRepository {
    private final QGenre qGenre = QGenre.genre;

    protected GenreCustomRepositoryImpl(EntityManager entityManager) {
        super(Genre.class, entityManager);
    }


    @Override
    public void saveAll(BaseObject objectKeyId) {

    }

    @Override
    public List<Genre> getGenresSearch(GenreSearchDto search) {
        BooleanBuilder builder = buildSearchCondition(search);
        QBean<Genre> qBean = buildBean();
        return queryFactory.select(qBean).from(qGenre)
                .where(builder)
                .fetch();
    }

    @Override
    public Optional<Genre> getGenre(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(qGenre)
                .where(qGenre.id.eq(id).and(qGenre.state.ne(ObjectState.DELETED)))
                .fetchOne());
    }

    private BooleanBuilder buildSearchCondition(GenreSearchDto search) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(search.getName())) {
            builder.and(qGenre.name.startsWith(search.getName()));
        }
        if (!ObjectUtils.isEmpty(search.getAddress())) {
            builder.and(qGenre.address.startsWith(search.getAddress()));
        }
        return builder.and(qGenre.state.ne(ObjectState.DELETED));

    }

    private QBean<Genre> buildBean() {
        return Projections.bean(
                Genre.class, qGenre.id, qGenre.name, qGenre.address, qGenre.state, qGenre.createdDate, qGenre.modifiedDate
        );
    }
}
