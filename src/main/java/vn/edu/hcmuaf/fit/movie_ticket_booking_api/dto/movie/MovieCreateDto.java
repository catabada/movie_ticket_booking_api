package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.ChangeToSlug;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MovieCreateDto {
    @Valid
    private MovieDto movie;
}
