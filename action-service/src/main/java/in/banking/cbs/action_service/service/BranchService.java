package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.DTO.BranchDto;
import in.banking.cbs.action_service.DTO.BranchDtoUpdate;
import in.banking.cbs.action_service.DTO.Credential;
import in.banking.cbs.action_service.DTO.Roles;
import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.Bank;
import in.banking.cbs.action_service.entity.Branch;
import in.banking.cbs.action_service.entity.User;
import in.banking.cbs.action_service.exception.InvalidDataException;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.repository.BankRepository;
import in.banking.cbs.action_service.repository.BranchRepository;
import in.banking.cbs.action_service.repository.UserRepository;
import in.banking.cbs.action_service.utility.MapObject;
import in.banking.cbs.action_service.utility.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final MapObject mapper;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final SecurityServiceClient securityServiceClient;
    private final BankRepository bankRepository;

    public Branch createBranch(BranchDto branchDto) {

//        Branch branch = mapper.mapDtoToBranch(branchDto);


        String bankName = branchDto.getBankName();

        Bank bank = bankRepository.findByBankName(bankName).orElseThrow(() -> new NotFoundException("Bank not exists with name : " + bankName));

        System.out.println("bank :"+bank);

        Branch branch = new Branch();

        branch.setBank(bank);
        branch.setCity(branchDto.getCity());
        branch.setAddress(branchDto.getAddress());
        branch.setState(branchDto.getAddress());
        branch.setContactNumber(branchDto.getContactNumber());
        branch.setIfscCode(branchDto.getIfscCode());
        branch.setBranchName(branchDto.getBranchName());

        int managerId = branchDto.getManagerId();

        if (managerId > 0) {
            Optional<User> optUser = userRepository.findById(managerId);

            System.out.println("inside if");

            if (optUser.isPresent()) {



                User user = optUser.get();
                int credentialId = user.getCredentialId();

                //find credentials by id and get role . if role is manager then proceed else throw error
                Credential credential = securityServiceClient.getCredentialById(credentialId);

                Optional<Roles> optRole = credential.getRoles().stream().filter(r -> r.getRole().equals(UserRole.MANAGER.name())).findAny();

                if (optRole.isEmpty()) {
                    throw new InvalidDataException("Invalid credential id : " + credentialId);
                }

                branch.setManagerId(user.getUserId());
            }
        }

        System.out.println("before save");

//        bank.getBranches().add(branch);
        branchRepository.save(branch);
        return branch;
    }

    public Branch updateBranch(int branchId, BranchDtoUpdate branchDtoUpdate) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + branchId));

        // update allowed fields
        branch.setAddress(branchDtoUpdate.getAddress());
        branch.setCity(branchDtoUpdate.getCity());
        branch.setState(branchDtoUpdate.getState());
        branch.setIfscCode(branchDtoUpdate.getIfscCode());
        branch.setContactNumber(branchDtoUpdate.getContactNumber());
        branch.setManagerId(branchDtoUpdate.getManagerId());

        return branchRepository.save(branch);
    }

    public void deleteBranch(int branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + branchId));

        branchRepository.delete(branch);
    }
}
