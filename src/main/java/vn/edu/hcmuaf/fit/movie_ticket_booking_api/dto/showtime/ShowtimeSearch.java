package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.BranchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;

import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeSearch {
    private MovieDto movie;
    private BranchDto branch;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime startTime;
}
