package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat.SeatDto;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class InvoiceCreate extends InvoiceDto {

    @NotNull
    List<SeatDto> seats;

}
