package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreCreate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreUpdateDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.genre.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @RequestMapping("/search")
    public ResponseEntity<HttpResponse> getGenresSearch(@RequestBody GenreSearchDto search) {
        List<GenreDto> genreDto = genreService.getGenresSearch(search);
        return ResponseEntity.ok().body(HttpResponseSuccess.success(genreDto).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getGenre(@PathVariable Long id) throws BaseException {
        GenreDto genreDto = genreService.getGenre(id);
        return ResponseEntity.ok().body(HttpResponseSuccess.success(genreDto).build());
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createGenre(@RequestBody @Valid GenreCreate create) throws BaseException {
        GenreDto dto = genreService.createGenre(create.getGenre());
        return ResponseEntity.ok().body(HttpResponseSuccess.success(dto).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteGenre(@PathVariable Long id) throws BaseException {
        genreService.deleteGenre(id);
        return ResponseEntity.ok().body(HttpResponseSuccess.success().build());
    }

    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateGenre(@RequestBody @Valid GenreUpdateDto update) throws BaseException {
        return ResponseEntity.ok().body(HttpResponseSuccess.success(genreService.updateGenre(update)).build());
    }

}
