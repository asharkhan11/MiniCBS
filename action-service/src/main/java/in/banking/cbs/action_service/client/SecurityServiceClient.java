package in.banking.cbs.action_service.client;

import in.banking.cbs.action_service.DTO.Credential;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "spring-security")
public interface SecurityServiceClient {

    @PostMapping("/auth/register")
    Credential register(@RequestBody @Valid Credential credential);

}
