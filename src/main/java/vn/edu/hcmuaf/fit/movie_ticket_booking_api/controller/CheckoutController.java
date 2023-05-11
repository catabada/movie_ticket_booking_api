package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceCreate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment.CaptureMoMoConfirmResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment.VNPayResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.checkout.CheckoutService;


@RestController
@RequestMapping("/checkout")
@CrossOrigin("*")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<HttpResponse> checkout(@RequestBody InvoiceCreate invoiceCreate, HttpServletRequest request) throws Exception {
        switch (invoiceCreate.getPaymentMethod()) {
            case MOMO -> {
                return ResponseEntity.ok(HttpResponseSuccess.success(checkoutService.checkoutMomo(invoiceCreate)).build());
            }
            case VNPAY -> {
                return ResponseEntity.ok(HttpResponseSuccess.success(checkoutService.checkoutVNPay(invoiceCreate, request)).build());
            }
            default -> throw new BadRequestException("Phương thức thanh toán không hợp lệ");
        }
    }

    @RequestMapping("/momo/return")
    public ResponseEntity<HttpResponse> returnByMomo(CaptureMoMoConfirmResponse captureMoMoConfirmResponse) throws Exception {
        checkoutService.returnByMomo(captureMoMoConfirmResponse);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @GetMapping("/vnpay/return")
    public ResponseEntity<HttpResponse> returnByVNPay(VNPayResponse vnpayResponse) throws Exception {
//        paymentService.confirmPayment(vnpayResponse);
        return ResponseEntity.ok(HttpResponseSuccess.success(vnpayResponse).build());
    }


    @PostMapping("/cancel")
    public ResponseEntity<HttpResponse> cancelBooking() {
        // delete invoice
        System.out.println("cancel");
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }
}
