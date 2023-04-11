package vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {
    public static String getCurrentEmail() {
        String currentEmail = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentEmail = authentication.getName();
        }

        return currentEmail;
    }
}
