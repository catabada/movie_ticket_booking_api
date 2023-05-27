package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.UserLoginRequest;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.UserLoginResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_user.AppUserService;

@Controller()
@RequestMapping("/admin")
public class AdminController {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AdminController(AppUserService appUserService, AuthenticationManager authenticationManager) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody UserLoginRequest loginRequest) throws BaseException {
        UserLoginResponse result = appUserService.loginAdmin(loginRequest);
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
}
