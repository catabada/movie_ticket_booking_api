package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.MovieFormat;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.MovieState;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MovieSearchDto {
    private String name;
    private Integer duration;
    private MovieState movieState;
    private MovieFormat movieFormat;

}
