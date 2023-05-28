package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_mail;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.VerificationConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.VerificationToken;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.infrastructure.AppMailSender;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.AppUserMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.verification_token.VerificationTokenCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.AppUtils;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppMailServiceImpl implements AppMailService {
    @Value("${spring.mail.username}")
    private String username;

    @Value("${app.config.baseurl.verify.email}")
    private String baseUrlVerifyEmail;

    @Value("${app.config.baseurl.verify-email.reset-password}")
    private String baseUrlVerifyResetPassword;

    @Value("${app.config.url.login}")
    private String loginLink;

    private final SpringTemplateEngine templateEngine;

    private final AppMailSender appMailSender;
    private final VerificationTokenCustomRepository verificationTokenCustomRepository;

    private final AppUserMapper appUserMapper;

    @Autowired
    public AppMailServiceImpl(AppMailSender appMailSender, SpringTemplateEngine templateEngine, VerificationTokenCustomRepository verificationTokenCustomRepository, AppUserMapper appUserMapper) {
        this.appMailSender = appMailSender;
        this.templateEngine = templateEngine;
        this.verificationTokenCustomRepository = verificationTokenCustomRepository;
        this.appUserMapper = appUserMapper;
    }

    @Override
    @Async("threadPoolTaskExecutorForVerifyEmailRegister")
    @Transactional
    public void sendVerifyEmailRegister(AppUserDto dto) throws BadRequestException {
        VerificationToken token = VerificationToken.builder()
                .token(UUID.randomUUID())
                .appUser(appUserMapper.toAppUser(dto))
                .isSent(true)
                .isVerified(false)
                .lastSent(ZonedDateTime.now())
                .expiredDate(ZonedDateTime.now().plusSeconds(VerificationConstant.EXPIRATION_TIME))
                .state(ObjectState.ACTIVE)
                .name(VerificationConstant.VERIFICATION_REGISTER)
                .build();

        verificationTokenCustomRepository.saveAndFlush(token);

        boolean success = sendVerifyEmailRegister(dto.getEmail(), token.getToken().toString());

        if (!success) throw new BadRequestException("Gửi email xác thực thất bại");
    }

    @Override
    @Async("threadPoolTaskExecutorResendEmail")
    @Transactional
    public void resendEmailVerifyRegister(AppUserDto appUserDto) throws MessagingException, BadRequestException, IOException {
        Optional<VerificationToken> optional =
                verificationTokenCustomRepository.getVerificationTokenByEmailAndName(appUserDto.getEmail(), VerificationConstant.VERIFICATION_REGISTER);
        if (optional.isEmpty()) {
            sendVerifyEmailRegister(appUserDto);
            return;
        }

        VerificationToken verificationToken = optional.get();
        boolean success = sendVerifyEmailRegister(appUserDto.getEmail(), verificationToken.getToken().toString());
        if (!success) throw new BadRequestException("Resend verify email failed");


    }

    @Override
    @Async("threadPoolTaskExecutorForVerifyEmailResetPassword")
    @Transactional
    public void sendVerifyMailResetPassword(final AppUserDto dto) throws MessagingException, IOException, BadRequestException {
        VerificationToken verificationToken = VerificationToken.builder()
                .isSent(true)
                .token(UUID.randomUUID())
                .appUser(appUserMapper.toAppUser(dto))
                .isVerified(false)
                .lastSent(ZonedDateTime.now())
                .expiredDate(ZonedDateTime.now().plusSeconds(VerificationConstant.EXPIRATION_TIME))
                .state(ObjectState.ACTIVE)
                .name(VerificationConstant.VERIFICATION_RESET_PASSWORD)
                .build();

        verificationTokenCustomRepository.saveAndFlush(verificationToken);
        boolean success = sendVerifyMailResetPassword(dto.getEmail(), verificationToken.getToken().toString());

        if (!success) throw new BadRequestException("Send verify email to reset password failed");
    }

    @Override
    @Async("threadPoolTaskExecutorForSendEmailBookingTicket")
    public void sendEmailBookingTicket(String email, InvoiceDto invoice) throws
            BadRequestException {
        try {
            byte[] imageInBytes = AppUtils.generateQRCodeImage(invoice.getCode());

            Context context = new Context();
            context.setVariable("code", invoice.getCode());
            context.setVariable("movieName", invoice.getShowtime().getMovie().getName());
            context.setVariable("showTime", invoice.getShowtime().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy")));
            context.setVariable("room", invoice.getShowtime().getRoom().getName());
            context.setVariable("branch", invoice.getShowtime().getRoom().getBranch().getName());
            context.setVariable("seats", invoice.getTickets().stream().map(ticket -> ticket.getSeat().getCode()).collect(Collectors.joining(", ")));
            String html = templateEngine.process("booking_result", context);

            appMailSender.sendEmailWithImageInlined(username, email, html, "Booking ticket", true, imageInBytes);
        } catch (Exception e) {
            log.error("Send email booking ticket failed: " + e.getMessage());
            throw new BadRequestException("Send email booking ticket failed");
        }
    }


    private boolean sendVerifyEmailRegister(String email, String token) {
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

    private boolean sendVerifyMailResetPassword(String email, String token) {
        try {
            Context context = new Context();
            context.setVariable("user_verify_reset_password_link", baseUrlVerifyResetPassword + token);
            String html = templateEngine.process("forgot_password", context);

            appMailSender.sendMail(username, email, html, "Forgot password", true);
            return true;
        } catch (Exception e) {
            log.error("Send verify email to reset password failed: " + e.getMessage());
            return false;
        }
    }
}
