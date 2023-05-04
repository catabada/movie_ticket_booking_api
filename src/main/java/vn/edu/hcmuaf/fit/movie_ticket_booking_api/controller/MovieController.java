package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieCreateDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieUpdateDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.movie.MovieService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movie")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping("/search")
    public ResponseEntity<HttpResponse> getMoviesSearch(@RequestBody MovieSearchDto search) {
        List<MovieDto> movieDto = movieService.getMoviesSearch(search);
        return ResponseEntity.ok(HttpResponseSuccess.success(movieDto).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getMovie(@PathVariable Long id) throws BaseException {
        MovieDto movieDto = movieService.getMovie(id);
        return ResponseEntity.ok().body(HttpResponseSuccess.success(movieDto).build());
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<HttpResponse> getMovieBySlug(@PathVariable String slug) throws BaseException {
        MovieDto movieDto = movieService.getMovieBySlug(slug);
        return ResponseEntity.ok().body(HttpResponseSuccess.success(movieDto).build());
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createMovie(@RequestBody @Valid MovieCreateDto create) throws BaseException {
        MovieDto dto = movieService.createMovie(create.getMovie());
        return ResponseEntity.ok().body(HttpResponseSuccess.success(dto).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteMovie(@PathVariable Long id) throws BaseException {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().body(HttpResponseSuccess.success().build());
    }

    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateMovie(@RequestBody @Valid MovieUpdateDto update) throws BaseException {
        return ResponseEntity.ok().body(HttpResponseSuccess.success(movieService.updateMovie(update)).build());
    }
}

