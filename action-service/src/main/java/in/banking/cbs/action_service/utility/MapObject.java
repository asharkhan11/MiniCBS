package in.banking.cbs.action_service.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.banking.cbs.action_service.DTO.*;
import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.Bank;
import in.banking.cbs.action_service.entity.Branch;
import in.banking.cbs.action_service.entity.User;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class MapObject {

    private final ObjectMapper mapper;
    private final SecurityServiceClient securityServiceClient;
    private final BankRepository bankRepository;

    public User mapDtoToUser(UserDto userDto) {

        User user = mapper.convertValue(userDto, User.class); // UserBankBranch, email, password and role not mapped, map it manually

        Roles role = Roles.builder()
                .role(userDto.getRole().name())
                .build();

        Credential cred = Credential.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .roles(Set.of(role))
                .build();

        Bank bank = bankRepository.findByBankName(userDto.getBankName()).orElseThrow(()-> new NotFoundException("Bank not exists with name : "+ userDto.getBankName()));

//        Branch branch = bank.getBranches().stream().filter(b -> Objects.equals(b.getName(), userDto.getBranchName())).findAny().orElseThrow(() -> new NotFoundException("Branch not exists with name : " + userDto.getBranchName()));

        Credential credential = securityServiceClient.register(cred);


//        UserBankBranch userBankBranch = UserBankBranch.builder()
//                .user(user)
//                .bank(bank)
//                .branch(branch)
//                .build();


        user.setCredentialId(credential.getCredentialId());
//        user.getUserBankBranches().add(userBankBranch);

        return user;
    }

    public Bank mapDtoToBank(BankDto bankDto) {

        Bank bank = mapper.convertValue(bankDto, Bank.class);

        return bank;
    }

    public Branch mapDtoToBranch(BranchDto branchDto) {

        Branch branch = mapper.convertValue(branchDto, Branch.class); // manager and bank is not mapped, map it manually

        String bankName = branchDto.getBankName();

        Bank bank = bankRepository.findByBankName(bankName).orElseThrow(() -> new NotFoundException("Bank not exists with name : " + bankName));

//        Optional<Branch> optBranch = bank.getBranches().stream().filter(b -> Objects.equals(b.getName(), branchDto.getName())).findAny();
//
//        if(optBranch.isPresent()){
//            throw new AlreadyExistsException("Branch already exists with name : "+branchDto.getName());
//        }

        branch.setBank(bank);
//        bank.getBranches().add(branch);

        return branch;
    }
}
