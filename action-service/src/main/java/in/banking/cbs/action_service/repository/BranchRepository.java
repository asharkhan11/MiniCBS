package in.banking.cbs.action_service.repository;

import in.banking.cbs.action_service.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    
    Optional<Branch> findByBranchName(String branchName);
    
    Optional<Branch> findByIfscCode(String ifscCode);

    Optional<Branch> findByBranchNameOrIfscCode(String branchName, String ifscCode);
}
