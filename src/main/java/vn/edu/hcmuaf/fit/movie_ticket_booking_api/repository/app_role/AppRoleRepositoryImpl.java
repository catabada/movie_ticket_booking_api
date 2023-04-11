package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_role;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoleConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppRole;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.QAppRole;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.Optional;

@Repository
public class AppRoleRepositoryImpl extends AbstractCustomRepository<AppRole, Long> implements AppRoleRepository {
    public final static QAppRole qAppRole = QAppRole.appRole;

    protected AppRoleRepositoryImpl(EntityManager entityManager) {
        super(AppRole.class, entityManager);
    }

    @Override
    public void saveAll() {

    }

    @Override
    public Optional<AppRole> getByName(String name) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qAppRole)
                        .where(
                                qAppRole.name.eq(name)
                        )
                        .fetchOne()
        );
    }
}
