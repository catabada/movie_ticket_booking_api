package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_user;

import com.google.gson.Gson;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.BeanIdConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.FileConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoleConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.VerificationConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.domain.AppUserDomain;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.UserInfoDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.UserInfoUpdate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppRole;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.UserInfo;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.VerificationToken;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.infrastructure.AppJwtTokenProvider;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.AppUserMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.UserInfoMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.service.FileService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.service.image.ImageFileService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_role.AppRoleRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_user.AppUserCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.verification_token.VerificationTokenCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_mail.AppMailService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.AppUtils;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
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

    @Value("${facebook.appSecret}")
    private String appSecret;

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
    public void register(AppUserDto dto) throws BaseException {
        boolean userByEmail = appUserCustomRepository.existsByEmail(dto.getEmail());
        if (userByEmail) {
            throw new BadRequestException("Email đã tồn tại");
        }

        boolean userByPhone = appUserCustomRepository.findByPhone(dto.getPhone());
        if (userByPhone) {
            throw new BadRequestException("Số điện thoại đã tồn tại");
        }

        AppUser newUser = appUserMapper.toAppUser(dto);
        // Set role
        AppRole defaultRole = appRoleRepository.getByName(RoleConstant.ROLE_MEMBER).orElse(null);
        newUser.getAppRoles().add(defaultRole);

        // Random image
        newUser.getUserInfo().setAvatar(FileConstant.TEMP_PROFILE_IMAGE_BASE_URL + dto.getEmail());
        // Hash password
        newUser.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        // Save user and check save success
        newUser = appUserCustomRepository.saveAndFlush(newUser);

        // Send verify email
        appMailService.sendVerifyEmailRegister(appUserMapper.toAppUserDtoWithoutAppRolesAndVerificationTokens(newUser));
    }

    @Override
    public Boolean verifyEmail(String token) throws BaseException {
        try {
            VerificationToken verificationToken = verificationTokenCustomRepository.getVerificationTokenByToken(UUID.fromString(token))
                    .orElseThrow(() -> new BadRequestException("Token không hợp lệ"));

            if (verificationToken.getExpiredDate().isBefore(ZonedDateTime.now())) {
                throw new BadRequestException("Token đã hết hạn");
            }

            verificationToken.setIsVerified(true);
            verificationToken.getAppUser().setEnabled(true);
            verificationTokenCustomRepository.saveAndFlush(verificationToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void resendEmailVerifyRegister(String email) throws BaseException {
        try {
            AppUser user = appUserCustomRepository.getUserByEmail(email).orElseThrow(() -> new BadRequestException("Email không tồn tại"));
            appMailService.resendEmailVerifyRegister(appUserMapper.toAppUserDtoWithoutAppRolesAndVerificationTokens(user));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public UserLoginResponse login(UserLoginRequest loginRequest) throws BaseException {
        try {
            AppUserDomain appUserDomain = (AppUserDomain) loadUserByUsername(loginRequest.getEmail());
            if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), appUserDomain.getPassword())) {
                throw new BadRequestException("Email hoặc mật khẩu không đúng");
            }

            if (!appUserDomain.isAccountNonLocked()) {
                throw new BadRequestException("Tài khoản của bạn đã bị khóa");
            }

            if (!appUserDomain.isEnabled()) {
                throw new BadRequestException("Tài khoản của bạn chưa được kích hoạt");
            }

            // Generate token
            String userToken = appJwtTokenProvider.generateJwtToken(appUserDomain);

            return UserLoginResponse.builder()
                    .userId(appUserDomain.getUserId())
                    .token(userToken)
                    .email(appUserDomain.getEmail())
                    .avatar(appUserDomain.getAvatar())
                    .build();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public UserLoginResponse loginWithFacebook(String accessToken) throws BaseException {
        try {
            AppUserFacebook appUserFacebook = getFacebookProfile(accessToken);
            if (appUserFacebook == null) {
                throw new BadRequestException("Không thể lấy thông tin người dùng");
            }

            AppUser appUser = appUserCustomRepository.getUserByFacebookId(appUserFacebook.getId()).orElse(null);
            if (appUser == null) {
                appUser = appUserCustomRepository.getUserByEmail(appUserFacebook.getEmail()).orElse(null);
                if (appUser == null) {
                    appUser = new AppUser();
                    appUser.setEmail(appUserFacebook.getEmail());
                    appUser.setAccountNonLocked(true);
                    appUser.setEnabled(true);

                    AppRole defaultRole = appRoleRepository.getByName(RoleConstant.ROLE_MEMBER)
                            .orElseThrow(() -> new BadRequestException("Không tìm thấy role member"));
                    appUser.setAppRoles(new HashSet<>(Collections.singletonList(defaultRole)));

                    appUser.setUserInfo(new UserInfo());
                    appUser.getUserInfo().setFullName(appUserFacebook.getName());
                    // appUser.getUserInfo().setIsMale(appUserFacebook.getGender().equalsIgnoreCase("male"));
                    appUser.getUserInfo().setDateOfBirth(appUserFacebook.getBirthdayAsDate());
                    appUser.getUserInfo().setAvatar(appUserFacebook.getPicture().getUrl());

                }

                appUser.setFacebookId(appUserFacebook.getId());
                appUser = appUserCustomRepository.saveAndFlush(appUser);
            }

            AppUserDomain appUserDomain = new AppUserDomain(appUser);

            return UserLoginResponse.builder()
                    .userId(appUserDomain.getUserId())
                    .token(appJwtTokenProvider.generateJwtToken(appUserDomain))
                    .email(appUserDomain.getEmail())
                    .avatar(appUserDomain.getAvatar())
                    .build();
        } catch (Exception e) {
            throw new BadRequestException("Đăng nhập không thành công");
        }
    }

    private AppUserFacebook getFacebookProfile(String accessToken) {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, appSecret, Version.LATEST);
        return facebookClient.fetchObject("me", AppUserFacebook.class, Parameter.with("fields", "id,name,email,first_name,last_name,picture.width(250).height(250),birthday,gender"));
    }

    @Override
    public UserLoginResponse loginWithGoogle(String accessToken) throws BaseException {
        try {
            AppUserGoogle appUserGoogle = getGoogleProfile(accessToken);
            if (appUserGoogle == null) {
                throw new BadRequestException("Không thể lấy thông tin người dùng");
            }

            AppUser appUser = appUserCustomRepository.getUserByGoogleId(appUserGoogle.getSub()).orElse(null);
            if (appUser == null) {
                appUser = appUserCustomRepository.getUserByEmail(appUserGoogle.getEmail()).orElse(null);
                if (appUser == null) {
                    appUser = new AppUser();
                    appUser.setEmail(appUserGoogle.getEmail());
                    appUser.setAccountNonLocked(true);
                    appUser.setEnabled(true);
                    appUser.setUserInfo(new UserInfo());
                    appUser.getUserInfo().setFullName(appUserGoogle.getName());
                    appUser.getUserInfo().setAvatar(appUserGoogle.getPicture());

                    AppRole defaultRole = appRoleRepository.getByName(RoleConstant.ROLE_MEMBER).orElse(null);
                    if (defaultRole == null) {
                        throw new BadRequestException("Không tìm thấy role mặc định");
                    }

                    appUser.setAppRoles(new HashSet<>(Collections.singletonList(defaultRole)));

                }

                appUser.setGoogleId(appUserGoogle.getSub());
                appUser = appUserCustomRepository.saveAndFlush(appUser);
            }

            AppUserDomain appUserDomain = new AppUserDomain(appUser);

            return UserLoginResponse.builder()
                    .userId(appUser.getId())
                    .token(appJwtTokenProvider.generateJwtToken(appUserDomain))
                    .email(appUser.getEmail())
                    .avatar(appUser.getUserInfo().getAvatar())
                    .build();
        } catch (Exception e) {
            throw new BadRequestException("Đăng nhập không thành công");
        }
    }

    private AppUserGoogle getGoogleProfile(String accessToken) throws IOException {
        String link = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        return new Gson().fromJson(response, AppUserGoogle.class);
    }

    @Override
    public void updateProfile(UserInfoUpdate userInfoUpdate) throws BaseException {
        if (!AppUtils.getCurrentEmail().equals(userInfoUpdate.getEmail()))
            throw new BadRequestException("You should login to update your profile");

        AppUser appUser = appUserCustomRepository.getUserByEmail(AppUtils.getCurrentEmail())
                .orElseThrow(() -> new BadRequestException("Not found user: " + userInfoUpdate.getEmail()));

        if (!appUser.getAccountNonLocked()) {
            throw new BadRequestException("Your account is locked");
        }

        if (!appUser.getEnabled()) {
            throw new BadRequestException("Please verify your email");
        }


        appUser.getUserInfo().setFullName(userInfoUpdate.getFullName());
        appUser.getUserInfo().setIsMale(userInfoUpdate.getIsMale());
        appUser.getUserInfo().setAvatar(userInfoUpdate.getAvatar());
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

            VerificationToken token = verificationTokenCustomRepository.getVerificationTokenByEmailAndName(forgotPasswordRequest.getEmail(), VerificationConstant.VERIFICATION_RESET_PASSWORD)
                    .orElse(null);
            if (token != null && token.getExpiredDate().isAfter(ZonedDateTime.now())) {
                throw new BadRequestException("Please wait 5 minutes to send again");
            }

            appMailService.sendVerifyMailResetPassword(appUserMapper.toAppUserDtoWithoutAppRolesAndVerificationTokens(appUser));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
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
    public void uploadAvatar(String avatarUrl) throws BaseException {
        try {
            Optional<AppUser> optionalAppUser = appUserCustomRepository.getUserByEmail(AppUtils.getCurrentEmail());
            if (optionalAppUser.isEmpty())
                throw new BadRequestException("Not found user: " + AppUtils.getCurrentEmail());

            AppUser appUser = optionalAppUser.get();

            if (!appUser.getAccountNonLocked()) {
                throw new BadRequestException("Tài khoản của bạn đã bị khóa");
            }

            if (!appUser.getEnabled()) {
                throw new BadRequestException("Vui lòng xác thực email của bạn");
            }

            appUser.getUserInfo().setAvatar(avatarUrl);

            appUserCustomRepository.saveAndFlush(appUser);
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
