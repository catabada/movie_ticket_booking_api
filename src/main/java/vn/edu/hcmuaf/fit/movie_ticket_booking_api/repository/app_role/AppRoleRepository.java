package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_role;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoleConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppRole;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.Optional;

@NoRepositoryBean
public interface AppRoleRepository extends ICustomRepository<AppRole, Long> {
    Optional<AppRole> getByName(String name);
}
