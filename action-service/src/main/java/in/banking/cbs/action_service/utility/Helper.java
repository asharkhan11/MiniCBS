package in.banking.cbs.action_service.utility;

import in.banking.cbs.action_service.DTO.Credential;
import in.banking.cbs.action_service.DTO.Roles;
import in.banking.cbs.action_service.entity.Account;
import in.banking.cbs.action_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Helper {

    private final AccountRepository accountRepository;

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
