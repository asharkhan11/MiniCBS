package in.banking.cbs.action_service.security;


import in.banking.cbs.action_service.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        System.out.println(authHeader);
        System.out.println("in jwt filter");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Claims claims = jwtUtil.extractAllClaims(token);
                String username = claims.getSubject();

                if (username != null) {

                    if (jwtUtil.validateJwt(token)) {

                        String type = claims.get("type", String.class);

                        if (type != null && type.equals("active")) {

                            List<?> roles = claims.get("role", List.class);

                            List<GrantedAuthority> authorities = roles.stream()
                                    .map(Object::toString) // safely convert any type
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());

                            System.out.println("role : "+roles);
                            System.out.println("authorities :" + authorities);

                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken( username, token, authorities );

                            authentication.setDetails(
                                    new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }

                    }
                }
            } catch (Exception e) {
                log.error("Error in JWT authentication filter....");
                log.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }


}
