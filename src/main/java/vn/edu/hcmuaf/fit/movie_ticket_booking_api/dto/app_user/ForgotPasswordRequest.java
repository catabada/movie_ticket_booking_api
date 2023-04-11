package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ForgotPasswordRequest {
    private String email;
}
