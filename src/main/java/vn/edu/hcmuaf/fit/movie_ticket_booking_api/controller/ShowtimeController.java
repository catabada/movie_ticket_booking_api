package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.showtime.ShowtimeService;

import java.util.List;

@RestController
@RequestMapping("/showtime")
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @Autowired
    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @RequestMapping("/search")
    public ResponseEntity<HttpResponse> searchShowtime(@RequestBody @Valid ShowtimeSearch showtimeSearch) {
        List<ShowtimeDto> showtimeDtoList = showtimeService.searchShowtime(showtimeSearch);
        return ResponseEntity.ok(HttpResponseSuccess.success(showtimeDtoList).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getShowtimeById(@PathVariable("id") Long id) {
        ShowtimeDto showtimeDto = showtimeService.getShowtimeById(id);
        return ResponseEntity.ok(HttpResponseSuccess.success(showtimeDto).build());
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createShowtime(@RequestBody @Valid ShowtimeCreate showtimeCreate) {
        ShowtimeDto showtimeDto = showtimeService.createShowtime(showtimeCreate);
        return ResponseEntity.ok(HttpResponseSuccess.success(showtimeDto).build());
    }

    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateShowtime(@RequestBody @Valid ShowtimeUpdate showtimeUpdate) throws BadRequestException {
        ShowtimeDto showtimeDto = showtimeService.updateShowtime(showtimeUpdate);
        return ResponseEntity.ok(HttpResponseSuccess.success(showtimeDto).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteShowtime(@PathVariable("id") Long id) throws BadRequestException {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.ok(HttpResponseSuccess.success("Delete showtime successfully").build());
    }
}
