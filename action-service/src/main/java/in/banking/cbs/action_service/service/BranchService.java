package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.entity.Branch;
import in.banking.cbs.action_service.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }
}
