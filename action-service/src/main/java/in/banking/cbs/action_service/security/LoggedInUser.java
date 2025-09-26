package in.banking.cbs.action_service.security;

import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.Admin;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggedInUser {

    private final SecurityServiceClient securityServiceClient;
    private final AdminRepository adminRepository;

    public Admin getLoggedInAdmin(){

        int credentialId = securityServiceClient.getCredential().getCredentialId();
        return adminRepository.findByCredentialId(credentialId).orElseThrow(() -> new NotFoundException("Admin not found"));

    }

}
