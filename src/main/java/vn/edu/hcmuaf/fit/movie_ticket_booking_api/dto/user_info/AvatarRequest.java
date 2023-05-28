package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AvatarRequest {
    @NotBlank(message = "Ảnh đại diện không được để trống")
    private String avatarUrl;
}
