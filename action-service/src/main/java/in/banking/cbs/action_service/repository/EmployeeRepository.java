package in.banking.cbs.action_service.repository;

import in.banking.cbs.action_service.entity.Branch;
import in.banking.cbs.action_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
    List<Employee> findAllByBranchIn(List<Branch> branches);

    List<Employee> findAllByBranch(Branch branch);

    Optional<Employee> findByCredentialId(int credentialId);
}
