package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo.ComboDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class InvoiceComboDto extends BaseObjectDto {
    private InvoiceDto invoice;
    private ComboDto combo;
    private int quantity;

}
