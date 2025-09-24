package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.DTO.BranchDto;
import in.banking.cbs.action_service.DTO.Credential;
import in.banking.cbs.action_service.DTO.Roles;
import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.Bank;
import in.banking.cbs.action_service.entity.Branch;
import in.banking.cbs.action_service.entity.User;
import in.banking.cbs.action_service.exception.InvalidDataException;
import in.banking.cbs.action_service.repository.BankRepository;
import in.banking.cbs.action_service.repository.BranchRepository;
import in.banking.cbs.action_service.repository.UserRepository;
import in.banking.cbs.action_service.utility.MapObject;
import in.banking.cbs.action_service.utility.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

        Branch branch = mapper.mapDtoToBranch(branchDto);
        int managerId = branchDto.getManagerId();

        if(managerId > 0) {
            Optional<User> optUser = userRepository.findById(managerId);

            if (optUser.isPresent()) {

                User user = optUser.get();
                int credentialId = user.getCredentialId();

                //find credentials by id and get role . if role is manager then proceed else throw error
                Credential credential = securityServiceClient.getCredentialById(credentialId);

                Optional<Roles> optRole = credential.getRoles().stream().filter(r -> r.getRole().equals(UserRole.MANAGER.name())).findAny();

                if(optRole.isEmpty()){
                    throw new InvalidDataException("Invalid credential id : "+ credentialId);
                }

                branch.setManagerId(user.getUserId());
            }
        }

        return branchRepository.save(branch);
    }

    public Branch updateBranch(int branchId, BranchDto branchDto) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + branchId));

        // update allowed fields
        branch.setName(branchDto.getName());
        branch.setAddress(branchDto.getAddress());
        branch.setCity(branchDto.getCity());
        branch.setState(branchDto.getState());
        branch.setIfscCode(branchDto.getIfscCode());
        branch.setContactNumber(branchDto.getContactNumber());
        branch.setManagerId(branchDto.getManagerId());

        // update bank if provided
        if (branchDto.getBankName() != null) {
            Bank bank = bankRepository.findByName(branchDto.getBankName())
                    .orElseThrow(() -> new RuntimeException("Bank not found: " + branchDto.getBankName()));
            branch.setBank(bank);
        }

        return branchRepository.save(branch);
    }

    public void deleteBranch(int branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + branchId));

        branchRepository.delete(branch);
    }
}
