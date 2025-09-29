package in.banking.cbs.query_service.controller;

import in.banking.cbs.query_service.DTO.AdminDto;
import in.banking.cbs.query_service.DTO.AdminResponseDto;
import in.banking.cbs.query_service.DTO.Credential;
import in.banking.cbs.query_service.DTO.Roles;
import in.banking.cbs.query_service.client.SecurityServiceClient;
import in.banking.cbs.query_service.entity.Admin;
import in.banking.cbs.query_service.message.Response;
import in.banking.cbs.query_service.security.LoggedInUser;
import in.banking.cbs.query_service.utility.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private LoggedInUser loggedInUser;
    private SecurityServiceClient securityServiceClient;

    public ResponseEntity<Response<AdminResponseDto>> getMyDetails(){

        AdminResponseDto adminResponseDto = new AdminResponseDto();

        Admin admin = loggedInUser.getLoggedInAdmin();

        int credentialId = admin.getCredentialId();

        Credential credential = securityServiceClient.getCredentialById(credentialId);

        String email = credential.getUsername();
        String password = credential.getPassword();
        Set<Roles> roles = credential.getRoles();

        adminResponseDto.setFirstName(admin.getFirstName());
        adminResponseDto.setLastName(admin.getLastName());
        adminResponseDto.setDob(admin.getDob());
        adminResponseDto.setEmail(email);
        adminResponseDto.setPassword(password);
        adminResponseDto.setRoles(roles);
        adminResponseDto.setAddress(admin.getAddress());
        adminResponseDto.setPhone(admin.getPhone());

        Response<AdminResponseDto> response = Response.<AdminResponseDto>builder()
                .status(ResponseStatus.CREATED)
                .message("Bank created successfully")
                .data(adminResponseDto)
                .build();

        return ResponseEntity.ok(response);


    }
}
