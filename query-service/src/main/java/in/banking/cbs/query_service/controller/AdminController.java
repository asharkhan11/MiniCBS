package in.banking.cbs.query_service.controller;

import in.banking.cbs.query_service.DTO.AdminDto;
import in.banking.cbs.query_service.DTO.AdminResponseDto;
import in.banking.cbs.query_service.DTO.Credential;
import in.banking.cbs.query_service.DTO.Roles;
import in.banking.cbs.query_service.client.SecurityServiceClient;
import in.banking.cbs.query_service.entity.Admin;
import in.banking.cbs.query_service.message.Response;
import in.banking.cbs.query_service.security.LoggedInUser;
import in.banking.cbs.query_service.entity.Bank;
import in.banking.cbs.query_service.message.Response;
import in.banking.cbs.query_service.service.AdminService;
import in.banking.cbs.query_service.utility.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final AdminService adminService;

    @GetMapping("/{bankName}")
    public ResponseEntity<Response<Bank>> getBankByName(@PathVariable String bankName){
        Bank bankByName = adminService.getBankByName(bankName);

        Response<Bank> response = Response.<Bank>builder()
                .status(in.banking.cbs.query_service.utility.ResponseStatus.FETCHED)
                .message("Bank fetched Successfully")
                .data(bankByName)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Response<Bank>> getBankByEmail(@PathVariable String email) {
        Bank bankByEmail = adminService.getBankByEmail(email);

        Response<Bank> response = Response.<Bank>builder()
                .status(in.banking.cbs.query_service.utility.ResponseStatus.FOUND)
                .message("Bank fetched successfully")
                .data(bankByEmail)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<Response<List<Bank>>> getBanksByAddress(@PathVariable String address) {
        List<Bank> banks = adminService.getBanksByAddress(address);

        Response<List<Bank>> response = Response.<List<Bank>>builder()
                .status(in.banking.cbs.query_service.utility.ResponseStatus.FETCHED)
                .message("Banks fetched successfully")
                .data(banks)
                .build();

        return ResponseEntity.ok(response);
    }

      @GetMapping("/search/address/{keyword}")
    public ResponseEntity<Response<List<Bank>>> getBanksByAddressKeyword(@PathVariable String keyword) {
        List<Bank> banks = adminService.getBanksByAddressKeyword(keyword);

        Response<List<Bank>> response = Response.<List<Bank>>builder()
                .status(ResponseStatus.FETCHED)
                .message("Banks fetched successfully by keyword")
                .data(banks)
                .build();

        return ResponseEntity.ok(response);
    }

    
  
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
