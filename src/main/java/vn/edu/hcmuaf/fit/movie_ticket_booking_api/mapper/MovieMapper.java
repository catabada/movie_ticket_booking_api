package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Movie;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie toMovie(final MovieDto movieDto);

    @Named("toMovieDtoWithoutGenres")
    @Mapping(target = "genres", ignore = true)
    MovieDto toMovieDto(final Movie movie);

    List<Movie> toMoiveList(final List<MovieDto> movieDtoList);

}
