package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.moive;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Movie;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.movie.MovieMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.genre.GenreCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.movie.MovieCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.ChangeToSlug;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieCustomRepository movieCustomRepository;
    private final GenreCustomRepository genreCustomRepository;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieServiceImpl(final MovieCustomRepository movieCustomRepository, final GenreCustomRepository genreCustomRepository, final MovieMapper movieMapper) {
        this.movieCustomRepository = movieCustomRepository;
        this.genreCustomRepository = genreCustomRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDto> getMoviesSearch(MovieSearchDto search) {
        return movieMapper.toMovieDtoList(movieCustomRepository.getMoviesSearch(search));
    }

    @Override
    @Transactional
    public MovieDto createMovie(MovieDto movieDto) throws BaseException {
        if(!checkGenreExisted(movieDto.getGenres())) {
            throw new BadRequestException("Genre not found");
        }

        Movie movie = movieCustomRepository.saveAndFlush(movieMapper.toMovie(movieDto));
        movie.setSlug(ChangeToSlug.removeAccent(movie.getName() + "-" + movie.getId()));

        if (ObjectUtils.isEmpty(movie))
            throw new BadRequestException("Create movie failed");
        return movieMapper.toMovieDto(movie);
    }

    @Override
    @Transactional
    public MovieDto updateMovie(MovieDto movieDto) throws BaseException {
        Movie movie = movieCustomRepository.getMovie(movieDto.getId()).orElseThrow(
                () -> new BadRequestException("Movie not found")
        );
        if (ObjectUtils.isEmpty(movieCustomRepository.saveAndFlush(movieMapper.toMovie(movieDto)))) {
            throw new BadRequestException("Update movie failed");
        }
        return movieMapper.toMovieDto(movie);
    }

    @Override
    public MovieDto getMovie(Long id) throws BaseException {
        Movie movie = movieCustomRepository.getMovie(id).orElseThrow(
                () -> new BadRequestException("Movie not found")
        );
        return movieMapper.toMovieDto(movie);
    }

    @Override
    public MovieDto getMovieBySlug(String slug) throws BaseException {
        Movie movie = movieCustomRepository.getMovieBySlug(slug).orElseThrow(
                () -> new BadRequestException("Movie not found")
        );
        return movieMapper.toMovieDto(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) throws BaseException {
        Movie movie = movieCustomRepository.getMovie(id).orElseThrow(
                () -> new BadRequestException("Movie not found")
        );
        movie.setState(ObjectState.DELETED);
        movie.setDeletedDate(ZonedDateTime.now());
        if (ObjectUtils.isEmpty(movieCustomRepository.saveAndFlush(movie)))
            throw new BadRequestException("Delete movie failed");
    }

    private boolean checkGenreExisted(Set<GenreDto> genres) {
        for (GenreDto genre : genres) {
            if (!genreCustomRepository.getGenre(genre.getId()).isPresent()) {
                return false;
            }
        }
        return true;
    }
}
