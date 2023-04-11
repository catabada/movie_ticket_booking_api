package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserLoginRequest {
    @NotNull
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20)
//    Password must contain one uppercase letter
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{6,20}$", message = "Password must contain one uppercase letter")
    private String password;

}
