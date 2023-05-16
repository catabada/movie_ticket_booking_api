package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserGoogle {
    private String sub;
    private String email;
    private boolean email_verified;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}
