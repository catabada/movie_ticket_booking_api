package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.movie;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Genre;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Movie;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QGenre;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QMovie;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MoviesCustomRepositoryImpl extends AbstractCustomRepository<Movie, Long> implements MovieCustomRepository {
    private final QMovie qMovie = QMovie.movie;
    private final QGenre qGenre = QGenre.genre;

    protected MoviesCustomRepositoryImpl(EntityManager entityManager) {
        super(Movie.class, entityManager);
    }

    @Override
    public Optional<Movie> getMovie(Long id) {
        return queryFactory.selectFrom(qMovie)
                .leftJoin(qMovie.genres, qGenre).fetchJoin()
                .where(
                        qMovie.id.eq(id)
                                .and(
                                        qMovie.state.ne(ObjectState.DELETED)
                                                .or(qMovie.genres.isEmpty().and(qMovie.state.ne(ObjectState.DELETED)))
                                )
                ).stream().peek(
                        movie -> movie.setGenres(movie.getGenres().stream()
                                .filter(genre -> genre.getState() != ObjectState.DELETED)
                                .collect(Collectors.toList()))
                ).findFirst();
    }

    @Override
    public Optional<Movie> getMovieBySlug(String slug) {
        return queryFactory.selectFrom(qMovie)
                .leftJoin(qMovie.genres, qGenre).fetchJoin()
                .where(
                        qMovie.slug.eq(slug)
                                .and(
                                        qMovie.state.ne(ObjectState.DELETED)
                                                .or(qMovie.genres.isEmpty().and(qMovie.state.ne(ObjectState.DELETED)))
                                )
                ).stream().peek(
                        movie -> movie.setGenres(movie.getGenres().stream()
                                .filter(genre -> genre.getState() != ObjectState.DELETED)
                                .collect(Collectors.toList()))
                ).findFirst();
    }

    @Override
    public Optional<Movie> getMovieByName(String name) {
        return queryFactory.selectFrom(qMovie)
                .leftJoin(qMovie.genres, qGenre).fetchJoin()
                .where(
                        qMovie.name.eq(name)
                                .and(
                                        qMovie.state.ne(ObjectState.DELETED)
                                                .or(qMovie.genres.isEmpty().and(qMovie.state.ne(ObjectState.DELETED)))
                                )
                ).stream().peek(
                        movie -> movie.setGenres(movie.getGenres().stream()
                                .filter(genre -> genre.getState() != ObjectState.DELETED)
                                .collect(Collectors.toList()))
                ).findFirst();
    }

    @Override
    public List<Movie> getMoviesSearch(MovieSearchDto search) {
        BooleanBuilder builder = buildConditionSearch(search);
        return queryFactory.selectFrom(qMovie)
                .leftJoin(qMovie.genres, qGenre)
                .fetchJoin()
                .where(
                        builder.or(qMovie.genres.isEmpty().and(builder))
                )
                .fetch()
                .stream()
                .peek(movie -> movie.setGenres(movie.getGenres().stream()
                        .filter(genre -> genre.getState() != ObjectState.DELETED)
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
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
                .and(qMovie.state.ne(ObjectState.DELETED));
    }

}
