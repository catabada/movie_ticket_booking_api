package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.BeanIdConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.FileConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoleConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.UploadFile;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.domain.AppUserDomain;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.UserInfoDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.UserInfoUpdate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppRole;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.VerificationToken;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.infrastructure.AppJwtTokenProvider;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.AppUserMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.UserInfoMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.entity.MediaFile;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.service.FileService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.service.image.ImageFileService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_role.AppRoleRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_user.AppUserCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.verification_token.VerificationTokenCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_mail.AppMailService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.AppUtils;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Qualifier(BeanIdConstant.USER_DETAIL_SERVICE)
public class AppUserServiceImpl implements AppUserService {
    private final AppUserCustomRepository appUserCustomRepository;
    private final VerificationTokenCustomRepository verificationTokenCustomRepository;
    private final AppRoleRepository appRoleRepository;
    private final AppMailService appMailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppJwtTokenProvider appJwtTokenProvider;
    private final AppUserMapper appUserMapper;
    private final UserInfoMapper userInfoMapper;
    private final FileService imageFileService;

    @Autowired
    public AppUserServiceImpl(AppUserCustomRepository appUserCustomRepository,
                              VerificationTokenCustomRepository verificationTokenCustomRepository,
                              AppRoleRepository appRoleRepository, AppMailService appMailService,
                              AppUserMapper appUserMapper, BCryptPasswordEncoder bCryptPasswordEncoder,
                              AppJwtTokenProvider appJwtTokenProvider, UserInfoMapper userInfoMapper,
                              ImageFileService imageFileService) {
        this.appUserCustomRepository = appUserCustomRepository;
        this.verificationTokenCustomRepository = verificationTokenCustomRepository;
        this.appRoleRepository = appRoleRepository;
        this.appMailService = appMailService;
        this.appUserMapper = appUserMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.appJwtTokenProvider = appJwtTokenProvider;
        this.userInfoMapper = userInfoMapper;
        this.imageFileService = imageFileService;
    }

    @Override
    @Transactional
    public void register(AppUserDto dto) throws BaseException {
        try {
            // Check email exists
            boolean isExisted = appUserCustomRepository.existsByEmail(dto.getEmail());
            if (isExisted) {
                throw new BadRequestException("Email already exists");
            }

            AppUser user = appUserMapper.toAppUser(dto);
            // Set role
            AppRole defaultRole = appRoleRepository.getByName(RoleConstant.ROLE_MEMBER).orElse(null);
            user.getAppRoles().add(defaultRole);

            // Random image
            user.getUserInfo().setAvatar(FileConstant.TEMP_PROFILE_IMAGE_BASE_URL + dto.getEmail());
            // Hash password
            user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

            // Save user and check save success

            if (ObjectUtils.isEmpty(appUserCustomRepository.saveAndFlush(user))) {
                throw new BadRequestException("Register failed");
            }

            // Send verify email
            appMailService.sendVerifyEmailRegister(appUserMapper.toAppUserDtoWithoutAppRolesAndVerificationTokens(user));

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void verifyEmail(String token) throws BaseException {
        try {
            Optional<VerificationToken> optional = verificationTokenCustomRepository.getVerificationTokenByToken(UUID.fromString(token));
            if (optional.isEmpty()) {
                throw new BadRequestException("Invalid token");
            }
            if (optional.get().getExpiredDate().isBefore(ZonedDateTime.now())) {
                throw new BadRequestException("Token expired");
            }
            VerificationToken verificationToken = optional.get();
            verificationToken.setIsVerified(true);
            verificationToken.getAppUser().setEnabled(true);
            verificationTokenCustomRepository.saveAndFlush(verificationToken);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void resendEmailVerifyRegister(String email) throws BaseException {
        try {
            Optional<AppUser> optional = appUserCustomRepository.getUserByEmail(email);
            if (optional.isEmpty()) {
                throw new BadRequestException("Email not found");
            }
            AppUser user = optional.get();
            appMailService.resendEmailVerifyRegister(appUserMapper.toAppUserDtoWithoutAppRolesAndVerificationTokens(user));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserLoginResponse login(UserLoginRequest loginRequest) throws BaseException {
        try {
            AppUserDomain appUserDomain = (AppUserDomain) loadUserByUsername(loginRequest.getEmail());
            if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), appUserDomain.getPassword())) {
                throw new BadRequestException("Invalid email or password");
            }

            if (!appUserDomain.isAccountNonLocked()) {
                throw new BadRequestException("Your account is locked");
            }

            if (!appUserDomain.isEnabled()) {
                throw new BadRequestException("Please verify your email");
            }

            // Generate token
            String userToken = appJwtTokenProvider.generateJwtToken(appUserDomain);

            return UserLoginResponse.builder()
                    .userId(appUserDomain.getUserId())
                    .token(userToken)
                    .email(appUserDomain.getEmail()).build();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserLoginResponse loginAdmin(UserLoginRequest loginRequest) throws BaseException {
        try {
            AppUserDomain appUserDomain = (AppUserDomain) loadUserByUsername(loginRequest.getEmail());
            if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), appUserDomain.getPassword())) {
                throw new BadRequestException("Email hoặc mật khẩu không đúng");
            }

            if (!appUserDomain.isAccountNonLocked()) {
                throw new BadRequestException("Tài khoản của bạn đã bị khóa");
            }

            if (!appUserDomain.isEnabled()) {
                throw new BadRequestException("Vui lòng xác thực email của bạn");
            }
            System.out.println(appUserDomain.getAuthorities());

            if (appUserDomain.getAuthorities().stream()
                    .noneMatch(
                            role -> role.getAuthority().equals(RoleConstant.ROLE_ADMIN)
                                    || role.getAuthority().equals(RoleConstant.ROLE_MANAGER)
                    )
            ) {
                throw new BadRequestException("Bạn không có quyền truy cập vào trang này. Vui lòng liên hệ với quản trị viên để được hỗ trợ");
            }

            // Generate token
            String userToken = appJwtTokenProvider.generateJwtToken(appUserDomain);

            return UserLoginResponse.builder()
                    .userId(appUserDomain.getUserId())
                    .token(userToken)
                    .email(appUserDomain.getEmail()).build();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateProfile(UserInfoUpdate userInfoUpdate) throws BaseException {
        if (!AppUtils.getCurrentEmail().equals(userInfoUpdate.getEmail()))
            throw new BadRequestException("You should login to update your profile");

        Optional<AppUser> optional = appUserCustomRepository.getUserByEmail(AppUtils.getCurrentEmail());
        if (optional.isEmpty()) throw new BadRequestException("Not found user: " + userInfoUpdate.getEmail());

        AppUser appUser = optional.get();

        if (!appUser.getAccountNonLocked()) {
            throw new BadRequestException("Your account is locked");
        }

        if (!appUser.getEnabled()) {
            throw new BadRequestException("Please verify your email");
        }


        appUser.getUserInfo().setFullName(userInfoUpdate.getFullName());
        appUser.getUserInfo().setFirstName(userInfoUpdate.getFirstName());
        appUser.getUserInfo().setLastName(userInfoUpdate.getLastName());
        appUser.getUserInfo().setIsMale(userInfoUpdate.getIsMale());
        appUser.getUserInfo().setDateOfBirth(userInfoUpdate.getDateOfBirth());

        if (ObjectUtils.isEmpty(appUserCustomRepository.saveAndFlush(appUser))) {
            throw new BadRequestException("Update user is failed");
        }

    }

    @Override
    public UserInfoDto getProfile() throws BaseException {
        String email = AppUtils.getCurrentEmail();
        if (ObjectUtils.isEmpty(email)) throw new BadRequestException("You should login");
        Optional<AppUser> optional = appUserCustomRepository.getUserByEmail(email);

        if (optional.isEmpty()) throw new BadRequestException("Not found user: " + email);
        AppUser appUser = optional.get();

        if (!appUser.getAccountNonLocked()) {
            throw new BadRequestException("Your account is locked");
        }

        if (!appUser.getEnabled()) {
            throw new BadRequestException("Please verify your email");
        }

        return userInfoMapper.toUserInfoDto(appUser.getUserInfo());
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) throws BaseException {
        if (!AppUtils.getCurrentEmail().equals(changePasswordRequest.getEmail()))
            throw new BadRequestException("You should login to change password");

        Optional<AppUser> optional = appUserCustomRepository.getUserByEmail(AppUtils.getCurrentEmail());
        if (optional.isEmpty()) throw new BadRequestException("Not found user: " + changePasswordRequest.getEmail());
        AppUser appUser = optional.get();

        if (!appUser.getAccountNonLocked()) {
            throw new BadRequestException("Your account is locked");
        }

        if (!appUser.getEnabled()) {
            throw new BadRequestException("Please verify your email");
        }

        if (!bCryptPasswordEncoder.matches(changePasswordRequest.getPassword(), appUser.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }

//        Set new password
        appUser.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
        if (ObjectUtils.isEmpty(appUserCustomRepository.saveAndFlush(appUser))) {
            throw new BadRequestException("Change password is failed");
        }
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws BaseException {
        try {
            Optional<AppUser> optional = appUserCustomRepository.getUserByEmail(forgotPasswordRequest.getEmail());
            if (optional.isEmpty()) {
                throw new BadRequestException("User not found: " + forgotPasswordRequest.getEmail());
            }

            AppUser appUser = optional.get();

            if (!appUser.getEnabled()) {
                throw new BadRequestException("Please verify your email");
            }

            if (!appUser.getAccountNonLocked()) {
                throw new BadRequestException("Your account is locked");
            }

            appMailService.sendVerifyMailResetPassword(appUserMapper.toAppUserDtoWithoutAppRolesAndVerificationTokens(appUser));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void verifyEmailResetPassword(String token) throws BaseException {
        System.out.println(token);
        try {
            Optional<VerificationToken> optional = verificationTokenCustomRepository.getVerificationTokenByToken(UUID.fromString(token));

            if (optional.isEmpty()) {
                throw new BadRequestException("Invalid token");
            }
            VerificationToken verificationToken = optional.get();
            if (verificationToken.getExpiredDate().isBefore(ZonedDateTime.now())) {
                throw new BadRequestException("Token expired");
            }

            verificationToken.setIsVerified(true);
            verificationTokenCustomRepository.saveAndFlush(verificationToken);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void resetPassword(final ResetPasswordRequest resetPasswordRequest) throws BaseException {
        VerificationToken verificationToken = verificationTokenCustomRepository.getVerificationTokenByToken(
                UUID.fromString(
                        resetPasswordRequest.getToken())).orElseThrow(() -> new BadRequestException("Invalid token")
        );

        if (verificationToken.getExpiredDate().isBefore(ZonedDateTime.now())) {
            throw new BadRequestException("Token expired");
        }

        AppUser appUser = verificationToken.getAppUser();
        appUser.setPassword(bCryptPasswordEncoder.encode(resetPasswordRequest.getPassword()));
        appUserCustomRepository.saveAndFlush(appUser);

    }

    @Override
    public String uploadAvatar(MultipartFile file) throws BaseException {
        try {
            if (file.isEmpty()) throw new BadRequestException("File is empty");
            Optional<AppUser> optionalAppUser = appUserCustomRepository.getUserByEmail(AppUtils.getCurrentEmail());
            if (optionalAppUser.isEmpty())
                throw new BadRequestException("Not found user: " + AppUtils.getCurrentEmail());

            AppUser appUser = optionalAppUser.get();

            if (!appUser.getAccountNonLocked()) {
                throw new BadRequestException("Your account is locked");
            }

            if (!appUser.getEnabled()) {
                throw new BadRequestException("Please verify your email");
            }

            MediaFile mediaFile = imageFileService.uploadFile(appUser.getEmail(), file, UploadFile.AVATAR_IMAGE);
            appUser.getUserInfo().setAvatar(mediaFile.getPathFolder());

            appUserCustomRepository.saveAndFlush(appUser);

            return ServletUriComponentsBuilder
                    .fromCurrentContextPath().path(mediaFile.getPathFolder()).toUriString();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserCustomRepository.getUserByEmail(email);
        if (appUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        return new AppUserDomain(appUser.get());
    }
}
