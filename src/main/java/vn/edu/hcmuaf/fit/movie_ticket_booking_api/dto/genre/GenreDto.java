package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class GenreDto extends BaseObjectDto {
    @NotNull
    protected String name;
}
