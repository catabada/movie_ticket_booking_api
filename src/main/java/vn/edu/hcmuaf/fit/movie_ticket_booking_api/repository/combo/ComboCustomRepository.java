package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.combo;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Combo;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ComboCustomRepository extends ICustomRepository<Combo, Long> {
    Optional<Combo> getCombo(final Long id);

    List<Combo> getCombos();
}
