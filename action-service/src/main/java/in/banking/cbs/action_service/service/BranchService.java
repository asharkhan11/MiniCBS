package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.DTO.BranchDto;
import in.banking.cbs.action_service.entity.Branch;
import in.banking.cbs.action_service.entity.User;
import in.banking.cbs.action_service.repository.BranchRepository;
import in.banking.cbs.action_service.repository.UserRepository;
import in.banking.cbs.action_service.utility.MapObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final MapObject mapper;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;

    public Branch createBranch(BranchDto branchDto) {

        Branch branch = mapper.mapDtoToBranch(branchDto);
        int managerId = branchDto.getManagerId();

        if(managerId > 0) {
            Optional<User> optUser = userRepository.findById(managerId);

            if (optUser.isPresent()) {

                User user = optUser.get();
                int credentialId = user.getCredentialId();

                //find credentials by id and get role . if role is manager then proceed else throw error


            }
        }

        return branchRepository.save(branch);
    }
}
