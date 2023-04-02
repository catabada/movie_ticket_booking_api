package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.movie;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Movie;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.object_key.BaseObjectMapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper extends BaseObjectMapper {
    Movie toMovie(final MovieDto movieDto);

    MovieDto toMovieDto(final Movie movie);

    List<Movie> toMoiveList(final List<MovieDto> movieDtoList);

    List<MovieDto> toMovieDtoList(final List<Movie> movieList);

    @Named("toMovieDtoIgnoreGenres")
    @Mapping(target="genres", ignore = true)
    MovieDto toMovieDtoIgnoreGenres(final Movie movie);
}
