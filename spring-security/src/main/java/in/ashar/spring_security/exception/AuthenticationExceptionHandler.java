package in.ashar.spring_security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Use the real exception message from DaoAuthenticationProvider
        String realMessage = authException.getCause() != null
                ? authException.getCause().getMessage()
                : authException.getMessage();

        response.getWriter().write("""
            {
                "error": "Authentication Failed",
                "detail": "%s"
            }
            """.formatted(realMessage));
    }

}
