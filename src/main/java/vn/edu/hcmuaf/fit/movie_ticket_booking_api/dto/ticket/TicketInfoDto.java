package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TicketInfoDto {

    private String code;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime showtime;

    private String movie;

    private String room;

    private String seat;

    private String status;
}
