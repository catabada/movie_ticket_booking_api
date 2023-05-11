package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.room;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.RoomSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Room;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface RoomCustomRepository extends ICustomRepository<Room, Long> {
    Optional<Room> findByName(String name);

    List<Room> getRoomsSearch(RoomSearch search);
}
