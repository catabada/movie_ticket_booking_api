package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
public class UserInfoDto extends BaseObjectDto {
    protected String fullName;
    protected Boolean isMale;
    protected String avatar;
    protected Date dateOfBirth;

    public UserInfoDto() {
        this.fullName = "";
        this.isMale = true;
        this.avatar = "";
        this.dateOfBirth = new Date();
    }

}
