package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TicketCreate extends TicketDto {
    @NotBlank(message = "Email is required")
    private String email;
}
