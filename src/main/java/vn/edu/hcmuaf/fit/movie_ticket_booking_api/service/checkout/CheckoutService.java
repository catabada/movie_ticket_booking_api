package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.checkout;

import jakarta.servlet.http.HttpServletRequest;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment.MomoResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;

public interface CheckoutService {
    MomoResponse checkoutMomo(InvoiceCreate invoiceCreate) throws Exception;
    String checkoutVNPay(InvoiceCreate invoiceCreate, HttpServletRequest request) throws Exception;
    InvoiceDto confirmBooking(InvoiceDto invoiceDto) throws BadRequestException;
}
