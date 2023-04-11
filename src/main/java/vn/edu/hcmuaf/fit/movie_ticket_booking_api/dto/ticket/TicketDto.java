package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.TicketStatus;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat.SeatDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.ShowtimeDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TicketDto extends BaseObjectDto {

    private String code;

    @JsonIgnoreProperties("tickets")
    private ShowtimeDto showtime;

    private SeatDto seat;

    private TicketStatus status;
}
