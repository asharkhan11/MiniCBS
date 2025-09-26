package in.banking.cbs.action_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.banking.cbs.action_service.DTO.*;
import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.Admin;
import in.banking.cbs.action_service.repository.AdminRepository;
import in.banking.cbs.action_service.utility.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ObjectMapper objectMapper;
    private final SecurityServiceClient securityServiceClient;
    private final AdminRepository adminRepository;

    public Admin register(AdminDto adminDto){

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
}
