package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Genre;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    Genre toGenre(final GenreDto genreDto);
    @Named("toGenreDtoWithoutMovies")
    @Mapping(target = "movies", ignore = true)
    GenreDto toGenreDtoWithoutMovies(final Genre genre);

    List<Genre> toGenreList(final List<GenreDto> genreDtoList);

}
