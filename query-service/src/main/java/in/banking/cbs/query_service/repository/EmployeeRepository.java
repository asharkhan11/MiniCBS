package in.banking.cbs.query_service.repository;

import in.banking.cbs.query_service.entity.Branch;
import in.banking.cbs.query_service.entity.Employee;
import in.banking.cbs.query_service.utility.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByBranchIn(List<Branch> branches);

    List<Employee> findAllByBranch(Branch branch);

    Optional<Employee> findByCredentialId(int credentialId);

    List<Employee> findByFirstName(String firstName);

    List<Employee> findBySalary(float salary);

    List<Employee> findByDob(LocalDate dob);

    Employee findByPhone(String phone);

    List<Employee> findByStatus(UserStatus status);

    List<Employee> findByBranch(Branch branch);
}
