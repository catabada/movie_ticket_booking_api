package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserLoginResponse {
    private Long userId;
    private String token;
    private String email;
    private String avatar;
}