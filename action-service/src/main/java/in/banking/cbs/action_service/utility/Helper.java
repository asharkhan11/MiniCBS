package in.banking.cbs.action_service.utility;

import in.banking.cbs.action_service.DTO.Credential;
import in.banking.cbs.action_service.DTO.Roles;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Helper {

    public Credential createCredential(String email, String password, String roleName){

        Roles role = Roles.builder()
                .role(roleName)
                .build();

        return Credential.builder()
                .username(email)
                .password(password)
                .roles(Set.of(role))
                .build();
    }
}
