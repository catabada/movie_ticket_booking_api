package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Genre;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.NotFoundException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.genre.GenreMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.genre.GenreCustomRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private GenreCustomRepository genreCustomRepository;
    private GenreMapper genreMapper;

    @Autowired
    public GenreServiceImpl(final GenreCustomRepository genreCustomRepository, final GenreMapper genreMapper) {
        this.genreCustomRepository = genreCustomRepository;
        this.genreMapper = genreMapper;
    }


    @Override
    public List<GenreDto> getGenresSearch(GenreSearchDto search) {
        List<GenreDto> genreDtoList = genreMapper.toGenreDtoList(genreCustomRepository.getGenresSearch(search));
        return genreDtoList;
    }

    @Override
    public GenreDto getGenre(Long id) throws BaseException{
        Optional<GenreDto> genreDto = Optional.ofNullable(
                genreCustomRepository.getGenre(id).map(
                        genreMapper::toGenreDto).orElseThrow(
                        () -> new BaseException("Genre not found")
                )
        );
        return genreDto.get();
    }

    @Override
    public GenreDto createGenre(GenreDto genreDto) throws BaseException {
        GenreDto dto = genreMapper.toGenreDto(
                genreCustomRepository.save(
                        genreMapper.toGenre(genreDto)
                )
        );
        if (ObjectUtils.isEmpty(dto)) {
            throw new BaseException("Create genre failed");
        }
        return dto;
    }

    @Override
    public void deleteGenre(Long id) throws BaseException {
        Genre genre = genreCustomRepository.getGenre(id).orElseThrow(
                () -> new NotFoundException("Genre not found")
        );
        genre.setState(ObjectState.DELETED);
        genre.setDeletedDate(LocalDateTime.now());

        if (ObjectUtils.isEmpty(genreCustomRepository.save(genre))) {
            throw new BaseException("Delete genre failed");
        }

    }

    @Override
    public GenreDto updateGenre(GenreDto genreDto) throws BaseException {
        Genre genre = genreCustomRepository.getGenre(genreDto.getId()).orElseThrow(
                () -> new NotFoundException("Genre not found")
        );
        genre.setName(genreDto.getName());
        genre.setAddress(genreDto.getAddress());
        if (ObjectUtils.isEmpty(genreCustomRepository.save(genre))) {
            throw new BaseException("Update genre failed");
        }
        return genreMapper.toGenreDto(genre);
    }
}
