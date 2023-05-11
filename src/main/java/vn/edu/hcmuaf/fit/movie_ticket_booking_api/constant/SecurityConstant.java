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
            "/**",
            "/checkout/**",
            "/auth/resend-email/**",
            "/auth/register", "/auth/login", "/auth/login-facebook", "/auth/login-google",
            "/auth/verify-email/**",  "/user/image/profile/**",
            "/user/image/**", "/user/image/**", "/auth/forgot-password/**",
            "/checkout/**",
            "/swagger-ui/**", "/webjars/**", "/v3/**", "/swagger-resources/**",
            "/ticket/booking", "/showtime/**", "/**",
    };

    public final static String[] PUBLIC_GET_URLS = {
            "/**",
            "/myfile/images/**",
            "/myfile/tracks/**",
            "/common/**",
            "/myfile/dowload/**",
    };

    public final static String[] REQUIRE_ADMIN_ROLE_URLS = {
            "/user/update-status",
            "/user/admin",
            "/app-status/**",
    };

}
