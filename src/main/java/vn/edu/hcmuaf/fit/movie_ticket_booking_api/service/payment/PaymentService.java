package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.payment;

import jakarta.servlet.http.HttpServletRequest;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment.MomoResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment.VNPayResponse;

public interface PaymentService {
    MomoResponse createMomoCapturePayment(InvoiceDto invoice) throws Exception;

    String createVNPayPayment(InvoiceDto invoice, HttpServletRequest request) throws Exception;

    void confirmPayment(InvoiceDto invoice) throws Exception;
}
