package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.checkout;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;

public interface CheckoutService {
    InvoiceDto checkout(InvoiceCreate invoiceCreate) throws BadRequestException;
    InvoiceDto confirmBooking(InvoiceDto invoiceDto) throws BadRequestException;
}
