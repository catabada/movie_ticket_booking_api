package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Genre;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface GenreMapper {

    @Named("toGenreDtoWithoutMovies")
    @Mapping(target = "movies", ignore = true)
    GenreDto toGenreDtoWithoutMovies(final Genre genre);

    Genre toGenre(final GenreDto genreDto);

    List<Genre> toGenreList(final List<GenreDto> genreDtoList);

}
