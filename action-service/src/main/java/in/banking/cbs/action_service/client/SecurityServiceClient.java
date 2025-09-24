package in.banking.cbs.action_service.client;

import in.banking.cbs.action_service.DTO.Credential;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "spring-security")
public interface SecurityServiceClient {

    @PostMapping("/auth/register-user")
    Credential register(@RequestBody Credential credential);

    @GetMapping("credential/{credentialId}")
    Credential getCredentialById(@PathVariable int credentialId);

    @PutMapping("/{credentialId}")
    Credential updateCredentialById(@PathVariable int credentialId, @RequestBody Credential credential);


    }
