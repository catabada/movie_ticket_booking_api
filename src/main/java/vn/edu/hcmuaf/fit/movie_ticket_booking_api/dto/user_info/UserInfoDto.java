package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.infrastructure.DateConstant;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
public class UserInfoDto extends BaseObjectDto {
    @NotBlank(message = "Họ tên không được để trống")
    protected String fullName;

    protected Boolean isMale;

    @NotBlank(message = "Ảnh đại diện không được để trống")
    protected String avatar;

    @DateConstant
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    protected Date dateOfBirth;

    public UserInfoDto() {
        this.fullName = "";
        this.isMale = true;
        this.avatar = "";
        this.dateOfBirth = new Date();
    }

}
