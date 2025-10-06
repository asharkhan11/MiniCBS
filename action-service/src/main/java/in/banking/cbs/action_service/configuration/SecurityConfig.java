package in.banking.cbs.action_service.configuration;


import in.banking.cbs.action_service.exception.AuthenticationExceptionHandler;
import in.banking.cbs.action_service.exception.AuthorizationExceptionHandler;
import in.banking.cbs.action_service.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(csrf ->
//                        csrf
//                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                                .ignoringRequestMatchers("/auth/**")
//                )

                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/auth/**","/v3/api-docs/**").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> {
                    e
                    .authenticationEntryPoint(new AuthenticationExceptionHandler())
                    .accessDeniedHandler((req, res, ex) -> {
                        if (!res.isCommitted()) {
                            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                        }
                    });
                })
                .build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder);
//
//        return new ProviderManager(authenticationProvider);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new CustomUserDetailService();
//    }

//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }


}
