package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.movie;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Movie;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QGenre;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QMovie;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class MoviesCustomRepositoryImpl extends AbstractCustomRepository<Movie, Long> implements MovieCustomRepository {
    private final QMovie qMovie = QMovie.movie;
    private final QGenre qGenre = QGenre.genre;

    protected MoviesCustomRepositoryImpl(EntityManager entityManager) {
        super(Movie.class, entityManager);
    }

    @Override
    public Optional<Movie> getMovie(Long id) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qMovie)
                        .innerJoin(qMovie.genres, qGenre).fetchJoin()
                        .where(
                                qMovie.id.eq(id)
                                        .and(qMovie.state.ne(ObjectState.DELETED))
                                        .and(qGenre.state.ne(ObjectState.DELETED))
                        )
                        .fetchOne());
    }

    @Override
    public Optional<Movie> getMovieBySlug(String slug) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qMovie)
                        .innerJoin(qMovie.genres, qGenre).fetchJoin()
                        .where(
                                qMovie.slug.eq(slug)
                                        .and(qMovie.state.ne(ObjectState.DELETED))
                                        .and(qGenre.state.ne(ObjectState.DELETED))
                        )
                        .fetchOne()
        );
    }

    @Override
    public List<Movie> getMoviesSearch(MovieSearchDto search) {
        BooleanBuilder builder = buildConditionSearch(search);
        return queryFactory.selectFrom(qMovie)
                .innerJoin(qMovie.genres, qGenre).fetchJoin()
                .where(builder.and(qGenre.state.ne(ObjectState.DELETED)))
                .fetch();
    }

    @Override
    public void saveAll() {

    }

    private BooleanBuilder buildConditionSearch(MovieSearchDto search) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(search.getName())) {
            builder.and(qMovie.name.containsIgnoreCase(search.getName()));
        }
        if (!ObjectUtils.isEmpty(search.getDuration())) {
            builder.and(qMovie.duration.gt(search.getDuration()));
        }
        if (!ObjectUtils.isEmpty(search.getMovieFormat())) {
            builder.and(qMovie.movieFormats.contains(search.getMovieFormat()));
        }
        if (!ObjectUtils.isEmpty(search.getMovieState())) {
            builder.and(qMovie.movieState.eq(search.getMovieState()));
        }
        return builder
                .and(qMovie.state.ne(ObjectState.DELETED))
                .and(qMovie.genres.any().state.ne(ObjectState.DELETED));
    }

    private QBean<Movie> buildBean() {
        return Projections.bean(
                Movie.class,
                qMovie.id,
                qMovie.name,
                qMovie.storyLine,
                qMovie.genres,
                qMovie.actors,
                qMovie.director,
                qMovie.producer,
                qMovie.imageUrl,
                qMovie.trailerUrl,
                qMovie.rating,
                qMovie.slug,
                qMovie.duration,
                qMovie.movieFormats,
                qMovie.movieState,
                qMovie.releaseDate,
                qMovie.state,
                qMovie.createdDate,
                qMovie.modifiedDate,
                qMovie.deletedDate
        );
    }
}
