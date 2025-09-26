package in.banking.cbs.query_service.repository;

import in.banking.cbs.query_service.entity.Branch;
import in.banking.cbs.query_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
    List<Employee> findAllByBranchIn(List<Branch> branches);

    List<Employee> findAllByBranch(Branch branch);
}
