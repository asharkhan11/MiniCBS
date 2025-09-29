package in.banking.cbs.action_service.security;

import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.Admin;
import in.banking.cbs.action_service.entity.Employee;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.repository.AdminRepository;
import in.banking.cbs.action_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggedInUser {

    private final SecurityServiceClient securityServiceClient;
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;

    public Admin getLoggedInAdmin(){

        int credentialId = securityServiceClient.getCredential().getCredentialId();
        return adminRepository.findByCredentialId(credentialId).orElseThrow(() -> new NotFoundException("Admin not found"));

    }

    public Employee getLoggedInEmployee(){

        int credentialId = securityServiceClient.getCredential().getCredentialId();
        return employeeRepository.findByCredentialId(credentialId).orElseThrow(() -> new NotFoundException("Employee not found"));

    }

}
