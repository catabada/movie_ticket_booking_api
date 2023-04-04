package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Genre;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.NotFoundException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.MovieGenreMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.GenreMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.genre.GenreCustomRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private GenreCustomRepository genreCustomRepository;

    private GenreMapper genreMapper;
    private final MovieGenreMapper movieGenreMapper;

    @Autowired
    public GenreServiceImpl(final GenreCustomRepository genreCustomRepository, final GenreMapper genreMapper, final MovieGenreMapper movieGenreMapper) {
        this.genreCustomRepository = genreCustomRepository;
        this.genreMapper = genreMapper;
        this.movieGenreMapper = movieGenreMapper;

    }


    @Override
    public List<GenreDto> getGenresSearch(GenreSearchDto search) {
        List<GenreDto> genreDtoList = movieGenreMapper.toGenreDtoList(genreCustomRepository.getGenresSearch(search));
        return genreDtoList;
    }

    @Override
    @Transactional
    public GenreDto getGenre(Long id) throws BaseException{
        Optional<GenreDto> genreDto = Optional.ofNullable(
                genreCustomRepository.getGenre(id).map(
                        movieGenreMapper::toGenreDto).orElseThrow(
                        () -> new BadRequestException("Genre not found")
                )
        );
        return genreDto.get();
    }

    @Override
    @Transactional
    public GenreDto createGenre(GenreDto genreDto) throws BaseException {
        Genre genre = genreCustomRepository.saveAndFlush(genreMapper.toGenre(genreDto));
        if (ObjectUtils.isEmpty(genre)) {
            throw new BadRequestException("Create genre failed");
        }
        return movieGenreMapper.toGenreDto(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(Long id) throws BaseException {
        Genre genre = genreCustomRepository.getGenre(id).orElseThrow(
                () -> new NotFoundException("Genre not found")
        );
        genre.setState(ObjectState.DELETED);
        genre.setDeletedDate(ZonedDateTime.now());

        if (ObjectUtils.isEmpty(genreCustomRepository.saveAndFlush(genre))) {
            throw new BadRequestException("Delete genre failed");
        }

    }

    @Override
    @Transactional
    public GenreDto updateGenre(GenreDto genreDto) throws BaseException {
        Genre genre = genreCustomRepository.getGenre(genreDto.getId()).orElseThrow(
                () -> new NotFoundException("Genre not found")
        );
        if (ObjectUtils.isEmpty(genreCustomRepository.save(genreMapper.toGenre(genreDto)))) {
            throw new BadRequestException("Update genre failed");
        }
        return movieGenreMapper.toGenreDto(genre);
    }
}
