package in.banking.cbs.action_service.utility;

import in.banking.cbs.action_service.DTO.Credential;
import in.banking.cbs.action_service.DTO.Roles;
import in.banking.cbs.action_service.entity.Account;
import in.banking.cbs.action_service.entity.Admin;
import in.banking.cbs.action_service.entity.Customer;
import in.banking.cbs.action_service.entity.Employee;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.repository.AccountRepository;
import in.banking.cbs.action_service.security.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Helper {

    private final AccountRepository accountRepository;
    private final LoggedInUser loggedInUser;

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


    public int generateOtp(){
        Random random = new Random();
        return  100000 + random.nextInt(900000);
    }

}
