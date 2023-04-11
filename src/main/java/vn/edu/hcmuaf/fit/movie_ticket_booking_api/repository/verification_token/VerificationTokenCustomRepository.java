package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.verification_token;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.VerificationToken;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTokenCustomRepository extends ICustomRepository<VerificationToken, Long> {
    Optional<VerificationToken> getVerificationTokenByToken(UUID token);
    Optional<VerificationToken> getVerificationToken(Long id);
    Optional<VerificationToken> getVerificationTokenByEmailAndName(String email, String name);

}
