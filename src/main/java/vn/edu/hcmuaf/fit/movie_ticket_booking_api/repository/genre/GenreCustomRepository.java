package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.genre;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreSearchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Genre;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenreCustomRepository extends ICustomRepository<Genre, BaseObject> {
    List<Genre> getGenresSearch(final GenreSearchDto search);

    Optional<Genre> getGenre(Long id);

}
