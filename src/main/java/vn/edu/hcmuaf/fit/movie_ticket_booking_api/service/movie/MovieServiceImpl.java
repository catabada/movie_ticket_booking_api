package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.movie;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Genre;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Movie;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.MovieGenreMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.MovieMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.genre.GenreCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.movie.MovieCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.ChangeToSlug;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieCustomRepository movieCustomRepository;
    private final GenreCustomRepository genreCustomRepository;
    private final MovieGenreMapper movieGenreMapper;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieServiceImpl(final MovieCustomRepository movieCustomRepository,
                            final GenreCustomRepository genreCustomRepository,
                            MovieGenreMapper movieGenreMapper,
                            final MovieMapper movieMapper) {
        this.movieCustomRepository = movieCustomRepository;
        this.genreCustomRepository = genreCustomRepository;
        this.movieGenreMapper = movieGenreMapper;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDto> getMoviesSearch(MovieSearchDto search) {
        return movieGenreMapper.toMovieDtoList(movieCustomRepository.getMoviesSearch(search));
    }

    @Override
    @Transactional
    public MovieDto createMovie(MovieDto movieDto) throws BaseException {
        if (!checkGenreExisted(movieDto.getGenres())) {
            throw new BadRequestException("Genre not found");
        }
        if (!ObjectUtils.isEmpty(movieCustomRepository.getMovieByName(movieDto.getName()))) {
            throw new BadRequestException("Movie name already exists");
        }

        List<Genre> genres = genreCustomRepository.findAllById(movieDto.getGenres().stream().map(GenreDto::getId).toList());

        Movie movie = movieCustomRepository.saveAndFlush(movieMapper.toMovie(movieDto));
        movie.setGenres(genres);
        movie.setSlug(ChangeToSlug.removeAccent(movie.getName()));

        if (ObjectUtils.isEmpty(movie))
            throw new BadRequestException("Create movie failed");
        return movieGenreMapper.toMovieDto(movie);
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
        return movieGenreMapper.toMovieDto(movie);
    }

    @Override
    public MovieDto getMovie(Long id) throws BaseException {
        Movie movie = movieCustomRepository.getMovie(id).orElseThrow(
                () -> new BadRequestException("Movie not found")
        );
        return movieGenreMapper.toMovieDto(movie);
    }

    @Override
    public MovieDto getMovieBySlug(String slug) throws BaseException {
        Movie movie = movieCustomRepository.getMovieBySlug(slug).orElseThrow(
                () -> new BadRequestException("Movie not found")
        );
        return movieGenreMapper.toMovieDto(movie);
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

    private boolean checkGenreExisted(List<GenreDto> genres) {
        for (GenreDto genre : genres) {
            if (genreCustomRepository.getGenre(genre.getId()).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
