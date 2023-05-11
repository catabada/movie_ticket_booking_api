package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.combo;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Combo;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QCombo;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ComboCustomRepositoryImpl extends AbstractCustomRepository<Combo, Long> implements ComboCustomRepository {
    public static final QCombo qCombo = QCombo.combo;

    protected ComboCustomRepositoryImpl(EntityManager entityManager) {
        super(Combo.class, entityManager);
    }

    @Override
    public void saveAll() {

    }

    @Override
    public Optional<Combo> getCombo(Long id) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qCombo)
                        .where(qCombo.id.eq(id).and(qCombo.state.ne(ObjectState.DELETED)))
                        .fetchFirst()
        );
    }

    @Override
    public List<Combo> getCombos() {
        return queryFactory.selectFrom(qCombo)
                .where(qCombo.state.ne(ObjectState.DELETED))
                .fetch();
    }
}
