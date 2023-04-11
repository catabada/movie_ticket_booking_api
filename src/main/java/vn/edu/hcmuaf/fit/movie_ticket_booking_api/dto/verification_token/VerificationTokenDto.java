package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.verification_token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class VerificationTokenDto extends BaseObjectDto {
    protected UUID token;
    protected String name;
    protected AppUserDto appUser;
    protected Boolean isSent;
    protected ZonedDateTime lastSent;
    protected ZonedDateTime verifiedDate;
}
