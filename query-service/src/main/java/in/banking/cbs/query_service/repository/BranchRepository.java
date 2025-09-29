package in.banking.cbs.query_service.repository;

import in.banking.cbs.query_service.entity.Bank;
import in.banking.cbs.query_service.entity.Branch;
import in.banking.cbs.query_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    
    Optional<Branch> findByBranchName(String branchName);
    
    Optional<Branch> findByIfscCode(String ifscCode);

    Optional<Branch> findByBranchNameOrIfscCode(String branchName, String ifscCode);

    Optional<Branch> findByBranchNameAndBankBankName(String branchName, String bankName);

    Optional<Branch> findByBranchNameAndBank(String branchName, Bank bank);

    boolean existsByManager(Employee manager);

    List<Branch> findAllByBank(Bank bank);
}
