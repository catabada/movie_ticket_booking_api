package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import jakarta.validation.Valid;
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
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.AvatarRequest;
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
    private final AppUserService appUserService;

    @Autowired
    public UserController(AppUserService appUserService, AuthenticationManager authenticationManager) {
        this.appUserService = appUserService;
    }


    @GetMapping("/profile")
    public ResponseEntity<HttpResponse> getProfile() throws BaseException {
        return ResponseEntity.ok(HttpResponseSuccess.success(appUserService.getProfile()).build());
    }

    @PutMapping("/profile")
    public ResponseEntity<HttpResponse> updateProfile(@RequestBody @Valid UserInfoUpdate update) throws BaseException {
        appUserService.updateProfile(update);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());

    }

    @PutMapping("/change-password")
    public ResponseEntity<HttpResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws BaseException {
        appUserService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }

    @PutMapping("/upload-avatar")
    public ResponseEntity<HttpResponse> uploadAvatar(@RequestBody AvatarRequest avatarRequest) throws BaseException {
        appUserService.uploadAvatar(avatarRequest.getAvatarUrl());
        return ResponseEntity.ok(HttpResponseSuccess.success("Cập nhật ảnh đại diện thành công").data(avatarRequest.getAvatarUrl()).build());
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
