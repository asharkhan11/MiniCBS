package in.banking.cbs.query_service.service;

import in.banking.cbs.query_service.DTO.Credential;
import in.banking.cbs.query_service.client.SecurityServiceClient;
import in.banking.cbs.query_service.entity.Branch;
import in.banking.cbs.query_service.entity.Employee;
import in.banking.cbs.query_service.exception.NotFoundException;
import in.banking.cbs.query_service.repository.EmployeeRepository;
import in.banking.cbs.query_service.utility.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SecurityServiceClient securityServiceClient;

    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found with ID: " + employeeId));
    }

    public List<Employee> getEmployeesByFirstName(String firstName) {
        return employeeRepository.findByFirstName(firstName);
    }

    public List<Employee> getEmployeesBySalary(float salary) {
        return employeeRepository.findBySalary(salary);
    }

    public List<Employee> getEmployeesByDob(LocalDate dob) {
        return employeeRepository.findByDob(dob);
    }

    public Employee getEmployeesByPhone(String phone) {
        return employeeRepository.findByPhone(phone);
    }

    public List<Employee> getEmployeesByStatus(UserStatus status) {
        return employeeRepository.findByStatus(status);
    }

    public List<Employee> getEmployeesByBranch(Branch branch) {
        return employeeRepository.findByBranch(branch);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByEmail(String email) {
        Credential credentialByEmail = securityServiceClient.getCredentialByEmail(email);
        if (credentialByEmail == null){
            throw new NotFoundException("No credential found for email: " + email);
        }

        int credentialId = credentialByEmail.getCredentialId();
        Employee employee = employeeRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new NotFoundException("No Employee found with credential id: " + credentialId));

        return employee;
    }
}