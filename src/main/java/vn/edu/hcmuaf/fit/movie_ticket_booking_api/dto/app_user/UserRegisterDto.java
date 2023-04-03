package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserRegisterDto {
    private AppUserDto userRegister;

}
