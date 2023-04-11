package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_mail;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;

import java.io.IOException;
import java.util.Map;

public interface AppMailService {

    @Async("threadPoolTaskExecutorForVerifyEmail")
    void sendVerifyEmailRegister(AppUserDto dto) throws MessagingException, IOException, BadRequestException;

    @Async("threadPoolTaskExecutorResendMail")
    void resendEmailVerifyRegister(AppUserDto appUser) throws MessagingException, BadRequestException, IOException;

    @Async("threadPoolTaskExecutorForVerifyEmailResetPassword")
    void sendVerifyMailResetPassword(AppUserDto dto) throws MessagingException, IOException, BadRequestException;
}
