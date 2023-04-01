package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class GenreCreate {
    @Valid
    GenreDto genre;
}
