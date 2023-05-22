package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.NoticeType;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class NoticeDto extends BaseObjectDto {
    private String description;
    private NoticeType type;
    private Boolean isRead;

    @JsonIgnoreProperties("appUser")
    private AppUserDto appUser;
}
