package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_mail;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.infrastructure.AppMailSender;

import java.io.IOException;

@Service
@Slf4j
public class AppMailServiceImpl implements AppMailService {
    @Value("${spring.mail.username}")
    private String username;

    @Value("${app.config.baseurl.verify.email}")
    private String baseUrlVerifyEmail;

    @Value("${app.config.url.login}")
    private String loginLink;

    private SpringTemplateEngine templateEngine;

    private AppMailSender appMailSender;

    @Autowired
    public AppMailServiceImpl(AppMailSender appMailSender, SpringTemplateEngine templateEngine) {
        this.appMailSender = appMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async("threadPoolTaskExecutorForVerifyEmail")
    public Boolean sendVerifyEmailRegister(AppUserDto dto) throws MessagingException, IOException, BadRequestException {
        Boolean success = sendVerifyEmail(dto.getEmail(), "token");

        if (!success) throw new BadRequestException("Send verify email failed");
        return true;
    }

    @Override
    public Boolean resendMailVerify(AppUserDto appUser) {
        return null;
    }

    @Override
    public Boolean sendMailResetPassword(String email, String newPassword) {
        return null;
    }

    private boolean sendVerifyEmail(String email, String token) throws MessagingException, IOException {
        try {
            Context context = new Context();
            context.setVariable("user_verify_link", baseUrlVerifyEmail + token);
            String html = templateEngine.process("verify_email", context);

            appMailSender.sendMail(username, email, html, "Verify email", true);
            return true;
        } catch (Exception e) {
            log.error("Send verify email failed: " + e.getMessage());
            return false;
        }
    }
}
