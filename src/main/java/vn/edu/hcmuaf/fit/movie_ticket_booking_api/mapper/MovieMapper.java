package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Movie;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface MovieMapper {

    Movie toMovie(final MovieDto movieDto);

    @Named("toMovieDtoWithoutGenres")
    @Mapping(target = "genres", ignore = true)
    MovieDto toMovieDtoWithoutGenres(final Movie movie);

    List<Movie> toMovieList(final List<MovieDto> movieDtoList);
}
