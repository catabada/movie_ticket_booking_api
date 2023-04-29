package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class InvoiceCreate extends InvoiceDto {

    @NotNull
    @NotEmpty
    @Transient
    List<@NotEmpty String> seatCodes;
}
