package vn.edu.hcmuaf.fit.movie_ticket_booking_api.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.SecurityConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.infrastructure.AppJwtTokenProvider;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_user.AppUserService;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final AppJwtTokenProvider jwtTokenProvider;
    private final AppUserService appUserService;

    @Autowired
    public JwtAuthorizationFilter(AppJwtTokenProvider jwtTokenProvider, AppUserService appUserService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.appUserService = appUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD)) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstant.TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            // get token
            String token = authorizationHeader.replace(SecurityConstant.TOKEN_PREFIX, "");
            // get firstName from Token
            String email = jwtTokenProvider.getSubject(token);

            // validate token
            if (jwtTokenProvider.isTokenValid(email, token)) {

                // get privilege from token
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthoritiesFromToken(token);

                // set privilege -> SecurityContext
                Authentication authentication = jwtTokenProvider.getAuthentication(email, authorities, request);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

}
