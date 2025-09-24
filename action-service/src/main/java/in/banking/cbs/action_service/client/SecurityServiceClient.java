package in.banking.cbs.action_service.client;

import in.banking.cbs.action_service.DTO.Credential;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "spring-security")
public interface SecurityServiceClient {

    @PostMapping("/auth/register")
    Credential register(@RequestBody @Valid Credential credential);

    @GetMapping("credential/{credentialId}")
    Credential getCredentialById(@PathVariable int credentialId);

}
