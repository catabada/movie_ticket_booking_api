package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.Optional;

@Repository
public class AppUserCustomRepositoryImpl extends AbstractCustomRepository<AppUser, Long> implements AppUserCustomRepository {
    public static final QAppUser qAppUser = QAppUser.appUser;
    public static final QUserInfo qUserInfo = QUserInfo.userInfo;
    public static final QVerificationToken qVerificationToken = QVerificationToken.verificationToken;
    public static final QAppRole qAppRole = QAppRole.appRole;

    protected AppUserCustomRepositoryImpl(EntityManager entityManager) {
        super(AppUser.class, entityManager);
    }

    @Override
    public void saveAll() {

    }

    @Override
    public Optional<AppUser> getUser(Long id) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qAppUser)
                        .join(qAppUser.userInfo, qUserInfo).on(qUserInfo.state.ne(ObjectState.DELETED))
                        .join(qAppUser.verificationTokens, qVerificationToken).fetchJoin()
                        .join(qAppUser.appRoles, qAppRole).fetchJoin()
                        .where(qAppUser.id.eq(id)
                                .and(qAppUser.state.ne(ObjectState.DELETED))
                                .and(qVerificationToken.state.ne(ObjectState.DELETED))
                                .and(qAppRole.state.ne(ObjectState.DELETED))
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<AppUser> getUserByEmail(String email) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qAppUser)
                        .join(qAppUser.userInfo, qUserInfo).on(qUserInfo.state.ne(ObjectState.DELETED))
                        .join(qAppUser.verificationTokens, qVerificationToken).fetchJoin()
                        .join(qAppUser.appRoles, qAppRole).fetchJoin()
                        .where(qAppUser.email.eq(email)
                                .and(qAppUser.state.ne(ObjectState.DELETED))
                                .and(qVerificationToken.state.ne(ObjectState.DELETED))
                                .and(qAppRole.state.ne(ObjectState.DELETED))
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<AppUser> getUserByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public Optional<AppUser> getUserByFacebookId(String facebookId) {
        return Optional.empty();
    }

    @Override
    public Optional<AppUser> getUserByGoogleId(String googleId) {
        return Optional.empty();
    }


}
