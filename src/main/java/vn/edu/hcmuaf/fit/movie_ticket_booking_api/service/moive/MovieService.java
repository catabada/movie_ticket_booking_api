package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.moive;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<MovieDto> getMoviesSearch(MovieSearchDto search);

    MovieDto createMovie(MovieDto movieDto) throws BaseException;

    MovieDto updateMovie(MovieDto movieDto) throws BaseException ;

    MovieDto getMovie(Long id) throws BaseException ;

    MovieDto getMovieBySlug(String slug) throws BaseException;

    void deleteMovie(Long id) throws BaseException ;
}
