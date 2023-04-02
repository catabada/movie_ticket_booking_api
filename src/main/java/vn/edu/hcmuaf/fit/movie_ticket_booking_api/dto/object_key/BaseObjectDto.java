package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
public class BaseObjectDto {
    protected Long id;
    protected ObjectState state;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    protected ZonedDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    protected ZonedDateTime modifiedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    protected ZonedDateTime deletedDate;

    public BaseObjectDto() {
        this.state = ObjectState.ACTIVE;
    }
}
