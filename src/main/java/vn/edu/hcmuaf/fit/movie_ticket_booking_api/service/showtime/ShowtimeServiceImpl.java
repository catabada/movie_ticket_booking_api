package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.showtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Showtime;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.ShowtimeMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.showtime.ShowtimeCustomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {
    private final ShowtimeCustomRepository showtimeCustomRepository;
    private final ShowtimeMapper showtimeMapper;

    @Autowired
    public ShowtimeServiceImpl(ShowtimeCustomRepository showtimeCustomRepository, ShowtimeMapper showtimeMapper) {
        this.showtimeCustomRepository = showtimeCustomRepository;
        this.showtimeMapper = showtimeMapper;
    }


    @Override
    @Transactional
    public List<ShowtimeDto> searchShowtime(ShowtimeSearch showtimeSearch) {
        return showtimeMapper.toShowtimeDtoList(showtimeCustomRepository.searchShowtime(showtimeSearch));
    }

    @Override
    @Transactional
    public ShowtimeDto getShowtimeById(Long id) {
        Optional<Showtime> showtime = showtimeCustomRepository.findById(id);

        if (showtime.isEmpty()) {
            throw new RuntimeException("Showtime not found");
        }

        return showtimeMapper.toShowtimeDto(showtime.get());
    }

    @Override
    @Transactional
    public ShowtimeDto createShowtime(ShowtimeCreate showtimeCreate) {
        Showtime showtime = showtimeCustomRepository.saveAndFlush(showtimeMapper.toShowtime(showtimeCreate));

        return showtimeMapper.toShowtimeDto(showtime);
    }

    @Override
    @Transactional
    public ShowtimeDto updateShowtime(ShowtimeUpdate showtimeUpdate) {
        return null;
    }

    @Override
    @Transactional
    public void deleteShowtime(Long id) {

    }
}
