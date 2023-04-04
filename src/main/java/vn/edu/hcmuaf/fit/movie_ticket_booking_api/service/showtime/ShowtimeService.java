package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.showtime;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.*;

import java.util.List;

public interface ShowtimeService {
    List<ShowtimeDto> searchShowtime(ShowtimeSearch showtimeSearch);
    ShowtimeDto getShowtimeById(Long id);
    ShowtimeDto createShowtime(ShowtimeCreate showtimeCreate);
    ShowtimeDto updateShowtime(ShowtimeUpdate showtimeUpdate);
    void deleteShowtime(Long id);
}
