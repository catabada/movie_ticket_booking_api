package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.PaymentMethod;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.PaymentStatus;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class InvoiceSearch {
    private String code;
    private Long userId;
    private String email;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
}
