package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_mail;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;

import java.io.IOException;

public interface AppMailService {

    @Async("threadPoolTaskExecutorForVerifyEmail")
    void sendVerifyEmailRegister(AppUserDto dto) throws BadRequestException;

    @Async("threadPoolTaskExecutorResendMail")
    void resendEmailVerifyRegister(AppUserDto appUser) throws MessagingException, BadRequestException, IOException;

    @Async("threadPoolTaskExecutorForVerifyEmailResetPassword")
    void sendVerifyMailResetPassword(AppUserDto dto) throws MessagingException, IOException, BadRequestException;

    @Async("threadPoolTaskExecutorForSendEmailBookingTicket")
    void sendEmailBookingTicket(String email, InvoiceDto invoice) throws MessagingException, IOException,
            BadRequestException;
}
