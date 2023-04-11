package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.verification_token;

import jakarta.persistence.EntityManager;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.QAppRole;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.QAppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.QVerificationToken;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.VerificationToken;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.Optional;
import java.util.UUID;

public class VerificationTokenCustomRepositoryImpl extends AbstractCustomRepository<VerificationToken, Long> implements VerificationTokenCustomRepository {
    public static final QVerificationToken qVerificationToken = QVerificationToken.verificationToken;
    public static final QAppUser qAppUser = QAppUser.appUser;
    public static final QAppRole qAppRole = QAppRole.appRole;

    protected VerificationTokenCustomRepositoryImpl(EntityManager entityManager) {
        super(VerificationToken.class, entityManager);
    }

    @Override
    public Optional<VerificationToken> getVerificationTokenByToken(UUID token) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qVerificationToken)
                        .join(qVerificationToken.appUser, qAppUser).fetchJoin()
                        .where(
                                qVerificationToken.token.eq(token)
                                        .and(qVerificationToken.state.ne(ObjectState.DELETED))
                                        .and(qAppUser.state.ne(ObjectState.DELETED))
                        )
                        .fetchOne()
        );
    }


    @Override
    public Optional<VerificationToken> getVerificationToken(Long id) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qVerificationToken)
                        .where(
                                qVerificationToken.id.eq(id)
                                        .and(qVerificationToken.state.ne(ObjectState.DELETED))
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<VerificationToken> getVerificationTokenByEmailAndName(String email, String name) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qVerificationToken)
                        .join(qVerificationToken.appUser, qAppUser).fetchJoin()
                        .join(qAppUser.appRoles, qAppRole).fetchJoin()
                        .where(
                                qVerificationToken.appUser.email.eq(email)
                                        .and(qVerificationToken.name.eq(name))
                                        .and(qVerificationToken.state.ne(ObjectState.DELETED))
                                        .and(qAppUser.state.ne(ObjectState.DELETED))
                        )
                        .fetchOne()
        );
    }

    @Override
    public void saveAll() {

    }
}
