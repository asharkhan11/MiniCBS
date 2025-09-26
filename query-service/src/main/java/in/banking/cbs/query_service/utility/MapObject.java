package in.banking.cbs.query_service.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapObject {

//    private final ObjectMapper mapper;
//    private final SecurityServiceClient securityServiceClient;
//    private final BankRepository bankRepository;
//    private final BranchRepository branchRepository;

//    public User mapDtoToUser(UserDto userDto) {
//        String bankName = userDto.getBankName();
//        String branchName = userDto.getBranchName();
//
//        User user = mapper.convertValue(userDto, User.class); // email, password and role not mapped, map it manually
//
//        Roles role = Roles.builder()
//                .role(userDto.getRole().name())
//                .build();
//
//        Credential cred = Credential.builder()
//                .username(userDto.getEmail())
//                .password(userDto.getPassword())
//                .roles(Set.of(role))
//                .build();
//
//        Bank bank = bankRepository.findByBankName(bankName).orElseThrow(()-> new NotFoundException("Bank not exists with name : "+ bankName));
//
//        Branch branch = branchRepository.findByBranchNameAndBankBankName(branchName, bankName).orElseThrow(()-> new NotFoundException("Branch not exists with name : "+ branchName));
//
//        Credential credential = securityServiceClient.register(cred);
//
//
////        UserBankBranch userBankBranch = UserBankBranch.builder()
////                .user(user)
////                .bank(bank)
////                .branch(branch)
////                .build();
//
//
//        user.setCredentialId(credential.getCredentialId());
//        user.getBranches().add(branch);
//
//        return user;
//    }

//    public Bank mapDtoToBank(BankDto bankDto) {
//
//        Bank bank = mapper.convertValue(bankDto, Bank.class);
//
//        return bank;
//    }

//    public Branch mapDtoToBranch(BranchDto branchDto) {
//
//        Branch branch = mapper.convertValue(branchDto, Branch.class); // manager and bank is not mapped, map it manually
//
//        String bankName = branchDto.getBankName();
//
//        Bank bank = bankRepository.findByBankName(bankName).orElseThrow(() -> new NotFoundException("Bank not exists with name : " + bankName));

//        Optional<Branch> optBranch = bank.getBranches().stream().filter(b -> Objects.equals(b.getName(), branchDto.getName())).findAny();
//
//        if(optBranch.isPresent()){
//            throw new AlreadyExistsException("Branch already exists with name : "+branchDto.getName());
//        }

//        branch.setBank(bank);
////        bank.getBranches().add(branch);
//
//        return branch;
//    }
}
