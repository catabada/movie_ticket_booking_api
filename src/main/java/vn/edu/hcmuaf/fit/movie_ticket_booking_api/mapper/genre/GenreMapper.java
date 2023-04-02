package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.genre;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Genre;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.object_key.BaseObjectMapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper extends BaseObjectMapper {
    GenreDto toGenreDto(final Genre genre);

    Genre toGenre(final GenreDto genreDto);

    List<GenreDto> toGenreDtoList(final List<Genre> genreList);

    List<Genre> toGenreList(final List<GenreDto> genreDtoList);

}
