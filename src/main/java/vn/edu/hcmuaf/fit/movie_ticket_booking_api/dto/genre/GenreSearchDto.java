package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class GenreSearchDto {
    private String name;
    private String address;
}
