package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.UserInfoDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.UserInfoUpdate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;

import java.io.IOException;

public interface AppUserService extends UserDetailsService {
    void register(final AppUserDto appUserDto) throws BaseException;

    Boolean verifyEmail(final String token) throws BaseException;

    void resendEmailVerifyRegister(final String email) throws BaseException;

    UserLoginResponse login(final UserLoginRequest loginRequest) throws BaseException;
    UserLoginResponse loginAdmin(final UserLoginRequest loginRequest) throws BaseException;

    UserLoginResponse loginWithFacebook(final String accessToken) throws BaseException;

    UserLoginResponse loginWithGoogle(final String accessToken) throws BaseException, IOException;

    void updateProfile(final UserInfoUpdate userInfoUpdate) throws BaseException;

    UserInfoDto getProfile() throws BaseException;

    void changePassword(final ChangePasswordRequest changePasswordRequest) throws BaseException;

    void forgotPassword(final ForgotPasswordRequest forgotPasswordRequest) throws BaseException;

    void verifyEmailResetPassword(final String token) throws BaseException;

    void resetPassword(final ResetPasswordRequest resetPasswordRequest) throws BaseException;

    void uploadAvatar(final String avatarUrl) throws BaseException;

}
