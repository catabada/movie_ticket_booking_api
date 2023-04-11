package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserInfoUpdate extends UserInfoDto {
    private String email;
}
