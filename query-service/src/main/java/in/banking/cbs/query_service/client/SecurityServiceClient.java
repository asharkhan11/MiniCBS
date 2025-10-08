package in.banking.cbs.query_service.client;

import in.banking.cbs.query_service.DTO.Credential;
import in.banking.cbs.query_service.DTO.LoginResponse;
import in.banking.cbs.query_service.DTO.Roles;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "spring-security")
public interface SecurityServiceClient {

    @PostMapping("/auth/register-user")
    Credential register(@RequestBody Credential credential);

    @GetMapping("credential/{credentialId}")
    Credential getCredentialById(@PathVariable int credentialId);

    @PutMapping("credential/{credentialId}")
    Credential updateCredentialById(@PathVariable int credentialId, @RequestBody Credential credential);

    @PostMapping("auth/login")
    LoginResponse login(@RequestHeader("username") String username, @RequestHeader("password") String password);

    @GetMapping("/credential")
    Credential getCredential();

    @GetMapping("/role/{roleName}")
    Roles getRoleByName(@PathVariable String roleName);

    @DeleteMapping("/credential/ids")
    void deleteAllCredentialsByIds(@RequestBody List<Integer> credentialIds);

    @GetMapping("/credential/email/{email}")
    Credential getCredentialByEmail(@PathVariable String email);

    @PostMapping("/credential/password/match")
    boolean matchPassword(@RequestParam String rawPassword, @RequestParam String encodedPassword);
}