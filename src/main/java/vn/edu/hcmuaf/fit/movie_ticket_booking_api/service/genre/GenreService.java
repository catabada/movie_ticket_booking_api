package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.genre;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;

import java.util.List;

public interface GenreService {
    List<GenreDto> getGenresSearch(final GenreSearchDto search);

    GenreDto getGenre(Long id) throws BaseException;

    GenreDto createGenre(GenreDto genreDto) throws BaseException;

    void deleteGenre(Long id) throws BaseException;

    GenreDto updateGenre(GenreDto genreDto) throws BaseException;
}
