package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.PaymentMethod;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.PaymentStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class InvoiceSearch {
    private String code;
    private Long userId;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime startTime;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
}
