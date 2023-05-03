package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MomoResponse {
    private String partnerCode;

    private String orderId;

    private String requestId;

    private Long amount;

    private Date responseTime;

    private String message;

    private int resultCode;

    private String payUrl;

    private InvoiceDto invoice;
}
