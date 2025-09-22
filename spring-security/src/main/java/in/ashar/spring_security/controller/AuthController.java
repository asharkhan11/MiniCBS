package in.ashar.spring_security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ashar.spring_security.dto.AuthResponse;
import in.ashar.spring_security.dto.CredentialDto;
import in.ashar.spring_security.exception.UnAuthenticatedException;
import in.ashar.spring_security.exception.UnAuthorizedException;
import in.ashar.spring_security.repository.RolesRepository;
import in.ashar.spring_security.repository.CredentialRepository;
import in.ashar.spring_security.service.CredentialService;
import in.ashar.spring_security.utility.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CredentialService credentialService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestHeader("username") String username, @RequestHeader("password") String password) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authenticated = authenticationManager.authenticate(authToken);

        if(!authenticated.isAuthenticated()){
            throw new UnAuthenticatedException("Authentication Failed");
        }

        UserDetails userDetails = (UserDetails) authenticated.getPrincipal();

        String jwt = jwtUtil.generateJwt(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt, refreshToken));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthResponse> generateAccessToken(@RequestHeader("refresh-token") String refreshToken) {

        String userName = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (!jwtUtil.validateJwt(refreshToken, userDetails)) {
            throw new UnAuthorizedException("JWT validation Failed");
        }
        String jwt = jwtUtil.generateJwt(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt, refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity<CredentialDto> register(@RequestBody @Valid CredentialDto credentialDto) {
        return ResponseEntity.ok(credentialService.createCredential(credentialDto));

    }


    @GetMapping("/validate")
    public boolean validateToken(@RequestHeader("token") String token){

        try {

            String username = jwtUtil.extractUsername(token);
            System.out.println(username);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateJwt(token, userDetails)){
                log.info("valid token : {}",token);
                return true;
            }

        } catch (Exception e){
            log.error("exception : {}", e.getMessage());
        }

        log.info("invalid token : {}",token);

        return false;
    }


}
