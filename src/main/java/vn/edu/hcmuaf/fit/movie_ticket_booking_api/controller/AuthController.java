package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_user.AppUserService;

import java.net.URI;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Value("${app.config.url.reset-password}")
    private String urlVerifySuccess;
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AppUserService appUserService, AuthenticationManager authenticationManager) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody UserRegisterDto register) throws BaseException {
        appUserService.register(register.getUserRegister());
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody UserLoginRequest loginRequest) throws BaseException {
        UserLoginResponse result = appUserService.login(loginRequest);
        if (!ObjectUtils.isEmpty(result)) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        }
        return ResponseEntity.ok(HttpResponseSuccess.success(result).build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<HttpResponse> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws BaseException {
        appUserService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @PutMapping("/reset-password")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws BaseException {
        appUserService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @PostMapping("/resend-email/register")
    public ResponseEntity<HttpResponse> resendEmailVerifyRegister(@RequestBody ResendMailVerifyRegisterRequest resendMailVerifyRegisterRequest) throws BaseException {
        appUserService.resendEmailVerifyRegister(resendMailVerifyRegisterRequest.getEmail());
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @GetMapping("/verify-email/{token}")
    public ResponseEntity<HttpResponse> verifyEmail(@PathVariable String token) throws BaseException {
        appUserService.verifyEmail(token);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @GetMapping("/verify-reset-password/{token}")
    public ResponseEntity<HttpResponse> verifyResetPassword(@PathVariable String token) throws BaseException {
        appUserService.verifyEmailResetPassword(token);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlVerifySuccess + token)).build();
    }
}
