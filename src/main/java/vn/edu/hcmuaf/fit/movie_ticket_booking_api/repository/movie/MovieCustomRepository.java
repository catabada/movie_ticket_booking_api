package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.movie;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Movie;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface MovieCustomRepository extends ICustomRepository<Movie, Long> {
    Optional<Movie> getMovie(Long id);

    Optional<Movie> getMovieBySlug(String slug);
    List<Movie> getMoviesSearch(MovieSearchDto search);
}
