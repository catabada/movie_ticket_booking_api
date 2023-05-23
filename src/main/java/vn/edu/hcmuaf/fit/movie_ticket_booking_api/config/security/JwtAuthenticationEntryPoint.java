package vn.edu.hcmuaf.fit.movie_ticket_booking_api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseError;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    //resolve exception will be handled in global AuthenticationException
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpResponse httpResponse = HttpResponseError.error(HttpStatus.UNAUTHORIZED, "Chưa xác thực!",
                "Bạn cần phải đăng nhập để sử dụng dịch vụ này.").build();

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
}
