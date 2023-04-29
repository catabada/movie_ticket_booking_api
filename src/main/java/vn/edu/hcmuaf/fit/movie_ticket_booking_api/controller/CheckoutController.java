package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceCreate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_mail.AppMailService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.checkout.CheckoutService;

import java.io.IOException;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final AppMailService appMailService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService, AppMailService appMailService) {
        this.checkoutService = checkoutService;
        this.appMailService = appMailService;
    }

    @PostMapping
    public ResponseEntity<HttpResponse> checkout(@RequestBody InvoiceCreate invoiceCreate) throws BadRequestException {
        InvoiceDto data = checkoutService.checkout(invoiceCreate);
        return ResponseEntity.ok(HttpResponseSuccess.success(data).build());
    }

    @PostMapping("/confirm")
    public ResponseEntity<HttpResponse> confirmBooking(@RequestBody InvoiceDto invoiceDto) throws BadRequestException, MessagingException, IOException {
        InvoiceDto data = checkoutService.confirmBooking(invoiceDto);
//        appMailService.sendEmailBookingTicket(invoiceDto.getEmail(), invoiceDto);
        return ResponseEntity.ok(HttpResponseSuccess.success(data).build());
    }

    @PostMapping("/cancel")
    public ResponseEntity<HttpResponse> cancelBooking() {
        // delete invoice
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }
}
