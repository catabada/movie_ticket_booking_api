package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.seat;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Seat;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Showtime;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.*;

@NoRepositoryBean
public interface SeatCustomRepository extends ICustomRepository<Seat, Long> {
    Optional<Seat> findByCode(String name);
}
