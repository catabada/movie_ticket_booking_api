package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.checkout.CheckoutService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.payment.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final CheckoutService checkoutService;

    @Autowired
    public PaymentController(PaymentService paymentService, CheckoutService checkoutService) {
        this.paymentService = paymentService;
        this.checkoutService = checkoutService;
    }

    @PostMapping("/momo/create")
    public ResponseEntity<HttpResponse> payByMomo(@RequestBody InvoiceDto invoiceDto) throws Exception {
        MomoResponse response = paymentService.createMomoCapturePayment(invoiceDto);
        return ResponseEntity.ok(HttpResponseSuccess.success(response).build());
    }

    @PostMapping("/momo/return")
    public ResponseEntity<HttpResponse> returnByMomo(CaptureMoMoConfirmResponse captureMoMoConfirmResponse) {
        if(captureMoMoConfirmResponse.getResultCode() == 0)
            return ResponseEntity.ok(HttpResponseSuccess.success(checkoutService).build());
        return ResponseEntity.ok(HttpResponseSuccess.success(captureMoMoConfirmResponse).build());
    }

    @PostMapping("/vn-pay/create")
    public ResponseEntity<HttpResponse> payByVNPay(@RequestBody InvoiceDto invoiceDto, HttpServletRequest request) throws Exception {
        String paymentUrl = paymentService.createVNPayPayment(invoiceDto, request);
        return ResponseEntity.ok(HttpResponseSuccess.success(paymentUrl).build());
    }

    @GetMapping("/vn-pay/return")
    public ResponseEntity<HttpResponse> returnByVNPay(VNPayResponse vnpayResponse) throws Exception {
//        paymentService.confirmPayment(vnpayResponse);
        return ResponseEntity.ok(HttpResponseSuccess.success(vnpayResponse).build());
    }
}
