package vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant;

public class SecurityConstant {
    public final static String COMPANY = "DOUBLE_A";
    public final static String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public final static String AUTHORITIES = "authorities";
    public final static String OPTIONS_HTTP_METHOD = "OPTIONS";
    public final static String TOKEN_PREFIX = "Bearer ";
    public final static String APPLICATION_NAME = "Movie Ticket Booking API";
    //10 days
    public final static long EXPIRATION_TIME = 864000000L;
    public final static String[] PUBLIC_URLS = {
            "/**/search",
            "/checkout/**",
//            "/user/image/profile/**",
//            "/user/image/**", "/user/image/**",
//            "/swagger-ui/**", "/webjars/**", "/v3/**", "/swagger-resources/**",
    };
    public final static String[] AUTHENTICATION_URLS = {
            "/auth/resend-email/**", "/auth/verify-email/**",
            "/auth/register", "/auth/login", "/auth/forgot-password",
            "/auth/reset-password", "/auth/login-facebook", "/auth/login-google",
    };

    public final static String[] PUBLIC_GET_URLS = {
            "/**",
            "/myfile/images/**",
            "/myfile/tracks/**",
            "/common/**",
            "/myfile/dowload/**",
    };

    public final static String[] REQUIRE_ADMIN_ROLE_URLS = {
            "/user/search",
    };
    public final static String[] REQUIRE_MANAGER_ROLE_URLS = {
            "/**/create",
            "/**/update",
            "/**/delete",
    };

}
