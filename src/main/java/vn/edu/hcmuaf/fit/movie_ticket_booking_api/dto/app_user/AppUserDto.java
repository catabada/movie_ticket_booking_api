package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_role.AppRoleDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.UserInfoDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.verification_token.VerificationTokenDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
public class AppUserDto extends BaseObjectDto {
    protected String email;
    protected String phone;
    protected String password;
    protected Boolean enabled;
    protected Boolean accountNonLocked;
    protected String facebookId;
    protected String googleId;
    protected Set<AppRoleDto> appRoles;
    protected UserInfoDto userInfo;
    protected Set<VerificationTokenDto> verificationTokens;

    protected AppUserDto() {
        this.enabled = false;
        this.accountNonLocked = true;
        this.facebookId = "";
        this.googleId = "";
        this.userInfo = new UserInfoDto();
        this.setAppRoles(new HashSet<>());
    }
}
