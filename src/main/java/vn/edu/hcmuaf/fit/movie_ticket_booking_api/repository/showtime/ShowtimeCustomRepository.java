package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.showtime;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.ShowtimeSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Showtime;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ShowtimeCustomRepository extends ICustomRepository<Showtime, Long> {
    List<Showtime> searchShowtime(ShowtimeSearch showtimeSearch);

    Optional<Showtime> getShowtime(Long id);
}
