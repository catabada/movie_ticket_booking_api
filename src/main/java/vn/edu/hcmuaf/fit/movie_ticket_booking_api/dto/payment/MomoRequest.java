package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MomoRequest {
    private String partnerCode;
    private String requestType;
    private String ipnUrl;
    private String redirectUrl;
    private String orderId;
    private Long amount;
    private String lang;
    private Boolean autoCapture;
    private String orderInfo;
    private String requestId;
    private String extraData;
    private String signature;
}
