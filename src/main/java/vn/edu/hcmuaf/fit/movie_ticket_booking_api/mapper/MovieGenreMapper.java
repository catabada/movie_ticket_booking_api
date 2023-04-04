package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Genre;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Movie;

import java.util.List;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = {GenreMapper.class, MovieMapper.class})
public interface MovieGenreMapper {
    @Named("toMovieDto")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toGenreDtoWithoutMovies")
    MovieDto toMovieDto(final Movie movie);

    @Named("toGenreDto")
    @Mapping(target = "movies", source = "movies", qualifiedByName = "toMovieDtoWithoutGenres")
    GenreDto toGenreDto(final Genre genre);

    @IterableMapping(qualifiedByName = "toMovieDto")
    List<MovieDto> toMovieDtoList(final List<Movie> movieList);

    @IterableMapping(qualifiedByName = "toGenreDto")
    List<GenreDto> toGenreDtoList(final List<Genre> genreList);
}
