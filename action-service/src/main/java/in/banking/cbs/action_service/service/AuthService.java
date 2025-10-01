package in.banking.cbs.action_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.banking.cbs.action_service.DTO.*;
import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.Admin;
import in.banking.cbs.action_service.entity.OtpResolver;
import in.banking.cbs.action_service.exception.AlreadyExistsException;
import in.banking.cbs.action_service.exception.InvalidDataException;
import in.banking.cbs.action_service.exception.OtpExpiredException;
import in.banking.cbs.action_service.repository.AdminRepository;
import in.banking.cbs.action_service.repository.OtpResolverRepository;
import in.banking.cbs.action_service.security.LoggedInUser;
import in.banking.cbs.action_service.utility.Helper;
import in.banking.cbs.action_service.utility.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ObjectMapper objectMapper;
    private final SecurityServiceClient securityServiceClient;
    private final AdminRepository adminRepository;
    private final RestTemplate restTemplate;
    private final Helper helper;
    private final OtpResolverRepository otpResolverRepository;

    public Admin register(AdminDto adminDto) {

        Credential credentialExists = securityServiceClient.getCredentialByEmail(adminDto.getEmail());

        if (credentialExists != null) {
            throw new AlreadyExistsException("user already registered with same email, please try using different mail");
        }

        Admin admin = objectMapper.convertValue(adminDto, Admin.class);

        Roles role = Roles.builder()
                .role(UserRole.ADMIN.name())
                .build();

        Credential cred = Credential.builder()
                .username(adminDto.getEmail())
                .password(adminDto.getPassword())
                .roles(Collections.singleton(role))
                .build();

        Credential credential = securityServiceClient.register(cred);

        admin.setCredentialId(credential.getCredentialId());

        return adminRepository.save(admin);

    }

    public LoginResponse login(LoginDto loginDto) {

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        return securityServiceClient.login(username, password);

    }

    public void sendResetPasswordMail(String email) {

        Credential credential = securityServiceClient.getCredentialByEmail(email);

        int otp = helper.generateOtp();

        String url = "http://email-service/mail/send/async/html";
        String subject = "Password reset email";
        String body = """
                
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Password Reset</title>
                </head>
                <body style="margin:0; padding:0; background-color:#f4f4f4; font-family: Arial, sans-serif;">
                
                    <table align="center" width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:8px; box-shadow:0 2px 5px rgba(0,0,0,0.1); margin-top:30px;">
                        <tr>
                            <td style="background-color:#4CAF50; color:#ffffff; padding:20px; text-align:center; font-size:20px; border-top-left-radius:8px; border-top-right-radius:8px;">
                                Password Reset Request
                            </td>
                        </tr>
                        <tr>
                            <td style="padding:30px; color:#333333; font-size:16px; line-height:1.6;">
                                <p>Hi <strong>%s</strong>,</p>
                
                                <p>We received a request to reset your password for your account.</p>
                
                                <p style="text-align:center; margin:30px 0;">
                                    <span style="display:inline-block; background-color:#f1f1f1; border:1px solid #ddd; padding:15px 25px; font-size:22px; font-weight:bold; letter-spacing:2px; color:#333333; border-radius:6px;">
                                        %d
                                    </span>
                                </p>
                
                                <p>If you did not request a password reset, you can safely ignore this email. Your account is safe.</p>
                
                                <p style="color:#888888; font-size:14px;">
                                    Note: This OTP is valid for only <strong>10 minutes</strong>.
                                </p>
                
                                <p style="margin-top:30px;">Thanks,<br>
                                <strong>The Customer Support Team</strong></p>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color:#f4f4f4; padding:15px; text-align:center; font-size:12px; color:#777;">
                                &copy; 2025 Your Company. All rights reserved.
                            </td>
                        </tr>
                    </table>
                
                </body>
                </html>
                
                """.formatted(email, otp);

        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("to", email)
                .queryParam("subject", subject)
                .queryParam("body", body)
                .build()
                .encode()
                .toUri();

        restTemplate.exchange(uri, HttpMethod.POST,HttpEntity.EMPTY,String.class);

        OtpResolver otpResolver = OtpResolver.builder()
                .otp(otp)
                .credentialId(credential.getCredentialId())
                .expiry(LocalDateTime.now().plusMinutes(10))
                .build();

        otpResolverRepository.save(otpResolver);

    }


    public void changePassword(int otp, String newPassword) {

        OtpResolver otpResolver = otpResolverRepository.findByOtp(otp).orElseThrow(() -> new OtpExpiredException("Otp is expired or invalid"));

        LocalDateTime expiry = otpResolver.getExpiry();

        if(expiry.isBefore(LocalDateTime.now())){
            otpResolverRepository.delete(otpResolver);
            throw new OtpExpiredException("Otp is expired or invalid");
        }

        int credentialId = otpResolver.getCredentialId();

        Credential credential = securityServiceClient.getCredentialById(credentialId);
        credential.setPassword(newPassword);

        securityServiceClient.updateCredentialById(credentialId, credential);

        otpResolverRepository.delete(otpResolver);

    }


}
