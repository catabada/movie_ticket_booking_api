package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeSearch {
    private String movie;
    private String branch;
    private ZonedDateTime startTime;
}
