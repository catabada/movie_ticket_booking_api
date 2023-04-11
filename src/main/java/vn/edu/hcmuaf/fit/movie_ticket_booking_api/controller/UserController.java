package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import org.mapstruct.ap.shaded.freemarker.ext.beans._BeansAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.FileConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.UserInfoUpdate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_user.AppUserService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${app.config.url.reset-password}")
    private String urlVerifySuccess;
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(AppUserService appUserService, AuthenticationManager authenticationManager) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody UserRegisterDto register) throws BaseException {
        appUserService.register(register.getUserRegister());
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @GetMapping("/verify-email/{token}")
    public ResponseEntity<HttpResponse> verifyEmail(@PathVariable String token) throws BaseException {
        appUserService.verifyEmail(token);
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

    @PostMapping("/resend-email/register")
    public ResponseEntity<HttpResponse> resendEmailVerifyRegister(@RequestBody ResendMailVerifyRegisterRequest resendMailVerifyRegisterRequest) throws BaseException {
        appUserService.resendEmailVerifyRegister(resendMailVerifyRegisterRequest.getEmail());
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<HttpResponse> getProfile(@PathVariable Long id) throws BaseException {
        return ResponseEntity.ok(HttpResponseSuccess.success(appUserService.getProfile(id)).build());
    }

    @PutMapping("/profile")
    public ResponseEntity<HttpResponse> updateProfile(@RequestBody UserInfoUpdate update) throws BaseException {
        appUserService.updateProfile(update);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());

    }

    @PutMapping("/change-password")
    public ResponseEntity<HttpResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws BaseException {
        appUserService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<HttpResponse> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws BaseException {
        appUserService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @GetMapping("/verify-reset-password/{token}")
    public ResponseEntity<HttpResponse> verifyResetPassword(@PathVariable String token) throws BaseException {
        appUserService.verifyEmailResetPassword(token);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlVerifySuccess + token)).build();
    }

    @PutMapping("/reset-password")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws BaseException {
        appUserService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @PutMapping("/upload-avatar")
    public ResponseEntity<HttpResponse> uploadAvatar(@RequestParam("avatar") MultipartFile file) throws BaseException {
        appUserService.uploadAvatar(file);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }
    @GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException, IOException {
        URL url = new URL(FileConstant.TEMP_PROFILE_IMAGE_BASE_URL + username);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            byte[] chunk = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

}
