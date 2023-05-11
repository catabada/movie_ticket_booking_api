package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_user;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.UserInfo;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.Optional;

@NoRepositoryBean
public interface AppUserCustomRepository extends ICustomRepository<AppUser, Long> {
    Optional<AppUser> getUser(final Long id);

    Optional<AppUser> getUserProfile(final Long id);

    Boolean existsByEmail(final String email);

    Boolean findByPhone(final String phone);

    Optional<AppUser> getUserByEmail(final String email);

    Optional<AppUser> getUserByPhone(final String phone);

    Optional<AppUser> getUserByFacebookId(final String facebookId);

    Optional<AppUser> getUserByGoogleId(final String googleId);

}
