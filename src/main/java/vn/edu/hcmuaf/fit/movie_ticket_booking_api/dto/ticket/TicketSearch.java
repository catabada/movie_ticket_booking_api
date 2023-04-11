package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TicketSearch {

    private String code;
}
