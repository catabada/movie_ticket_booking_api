package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat.SeatDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TicketDto extends BaseObjectDto {
    @JsonIgnoreProperties("room")
    private SeatDto seat;

    @JsonIgnoreProperties("tickets")
    private InvoiceDto invoice;
}
