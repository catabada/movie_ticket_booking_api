package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ComboCreate {
    private ComboDto combo;
}
