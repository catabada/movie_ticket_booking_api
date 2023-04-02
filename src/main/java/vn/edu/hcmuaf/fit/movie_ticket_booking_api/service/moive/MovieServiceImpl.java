package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.moive;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Movie;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.movie.MovieMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.movie.MovieCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.ChangeToSlug;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieCustomRepository movieCustomRepository;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieServiceImpl(MovieCustomRepository movieCustomRepository, MovieMapper movieMapper) {
        this.movieCustomRepository = movieCustomRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDto> getMoviesSearch(MovieSearchDto search) {
        return movieMapper.toMovieDtoList(movieCustomRepository.getMoviesSearch(search));
    }

    @Override
    @Transactional
    public MovieDto createMovie(MovieDto movieDto) throws BaseException {
        Movie movie = movieCustomRepository.save(movieMapper.toMovie(movieDto));
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
        movieDto.setSlug(ChangeToSlug.removeAccent(movieDto.getName() + "-" + movie.getId()));
        if(ObjectUtils.isEmpty(movieCustomRepository.save(movieMapper.toMovie(movieDto)))) {
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
    }
}
