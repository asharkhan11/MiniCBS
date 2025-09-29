package in.banking.cbs.action_service.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import in.banking.cbs.action_service.DTO.*;
import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.*;
import in.banking.cbs.action_service.exception.AlreadyExistsException;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.exception.UnAuthorizedException;
import in.banking.cbs.action_service.repository.*;
import in.banking.cbs.action_service.security.LoggedInUser;
import in.banking.cbs.action_service.utility.Helper;
import in.banking.cbs.action_service.utility.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ObjectMapper objectMapper;
    private final BankRepository bankRepository;
    private final BranchRepository branchRepository;
    private final SecurityServiceClient securityServiceClient;
    private final LoggedInUser loggedInUser;
    private final EmployeeRepository employeeRepository;
    private final Helper helper;
    private final CustomerRepository customerRepository;

    public Bank createBank(BankDto bankDto) {

        Bank bank = objectMapper.convertValue(bankDto, Bank.class);

        Admin admin = loggedInUser.getLoggedInAdmin();

        bank.setAdmin(admin);

        return bankRepository.save(bank);
    }


    public Bank updateBank(BankDto bankDto) {

        Admin admin = loggedInUser.getLoggedInAdmin();

        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));

        Optional<Bank> byName = bankRepository.findByBankName(bankDto.getBankName());
        if (byName.isPresent()) {
            throw new RuntimeException("Bank name '" + bankDto.getBankName() + "' already exits!");
        }

        bank.setHeadOfficeAddress(bankDto.getHeadOfficeAddress());
        bank.setBankName(bankDto.getBankName());
        bank.setContactNumber(bankDto.getContactNumber());
        bank.setEmail(bankDto.getEmail());
        return bankRepository.save(bank);
    }


    @Transactional
    public void deleteBank() {

        Admin admin = loggedInUser.getLoggedInAdmin();

        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));

        List<Branch> branches = branchRepository.findAllByBank(bank);

        List<Employee> employees = employeeRepository.findAllByBranchIn(branches);

        List<Integer> credentialIds = employees.stream().map(Employee::getCredentialId).toList();

        securityServiceClient.deleteAllCredentialsByIds(credentialIds);

        employeeRepository.deleteAll(employees);
        branchRepository.deleteAll(branches);
        bankRepository.delete(bank);

    }


    public Branch createBranch(BranchDto branchDto) {

        Admin admin = loggedInUser.getLoggedInAdmin();
        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));

        String branchName = branchDto.getBranchName();
        String ifscCode = branchDto.getIfscCode();

        branchRepository.findByBranchNameOrIfscCode(branchName, ifscCode).ifPresent(b -> {
            throw new AlreadyExistsException("Branch Already exists with Name : %S or IFSC Code : %s ".formatted(branchName, ifscCode));
        });

        Branch branch = objectMapper.convertValue(branchDto, Branch.class);

        branch.setBank(bank);

        return branchRepository.save(branch);
    }

    public Branch updateBranch(String branchName, BranchDtoUpdate branchDtoUpdate) {

        Admin admin = loggedInUser.getLoggedInAdmin();

        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));

        Branch branch = branchRepository.findByBranchNameAndBank(branchName, bank).orElseThrow(() -> new NotFoundException("Branch not Exists"));

        // update allowed fields
        branch.setAddress(branchDtoUpdate.getAddress());
        branch.setCity(branchDtoUpdate.getCity());
        branch.setState(branchDtoUpdate.getState());
        branch.setIfscCode(branchDtoUpdate.getIfscCode());
        branch.setContactNumber(branchDtoUpdate.getContactNumber());

        // update manager of branch
        int managerId = branchDtoUpdate.getManagerId();

        if (managerId > 0) {
            Employee manager = employeeRepository.findById(managerId).orElseThrow(() -> new NotFoundException("Manager not Exists"));

            if (branchRepository.existsByManager(manager)) {
                throw new AlreadyExistsException("Manager already assigned to other branch");
            }

            branch.setManager(manager);

            //update role of employee to branch
            int credentialId = manager.getCredentialId();
            Credential credential = securityServiceClient.getCredentialById(credentialId);
            Set<Roles> roles = credential.getRoles();

            Optional<Roles> role = roles.stream().filter(r -> r.getRole().equals(UserRole.MANAGER.name())).findAny();

            if (role.isEmpty()) {
                Roles managerRole = securityServiceClient.getRoleByName(UserRole.MANAGER.name());
                credential.getRoles().add(managerRole);
                securityServiceClient.updateCredentialById(credentialId, credential);
            }
        }
        return branchRepository.save(branch);
    }

    public void deleteBranch(String branchName) {

        Branch branch = branchRepository.findByBranchName(branchName).orElseThrow(() -> new RuntimeException("Branch not found with name: " + branchName));

        Admin admin = loggedInUser.getLoggedInAdmin();
        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));

        if (!branch.getBank().equals(bank)) {
            throw new UnAuthorizedException("Access Denied");
        }

        List<Employee> employees = employeeRepository.findAllByBranch(branch);

        List<Integer> credentialIds = employees.stream().map(Employee::getCredentialId).toList();
        securityServiceClient.deleteAllCredentialsByIds(credentialIds);

        employeeRepository.deleteAll(employees);
        branchRepository.delete(branch);
    }


    public Employee createEmployee(EmployeeDto employeeDto) {

        String branchName = employeeDto.getBranchName();
        Branch branch = branchRepository.findByBranchName(branchName).orElseThrow(() -> new RuntimeException("Branch not found with name: " + branchName));

        Admin admin = loggedInUser.getLoggedInAdmin();
        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));

        if (!branch.getBank().equals(bank)) {
            throw new UnAuthorizedException("Access Denied");
        }

        Employee employee = objectMapper.convertValue(employeeDto, Employee.class);
        employee.setBranch(branch);

        String email = employeeDto.getEmail();
        String password = employeeDto.getPassword();

        Credential cred = helper.createCredential(email, password, UserRole.EMPLOYEE.name());

        Credential credential = securityServiceClient.register(cred);

        employee.setCredentialId(credential.getCredentialId());

        return employeeRepository.save(employee);

    }

    public Employee updateEmployee(int employeeId, EmployeeDtoUpdate employeeDto) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException("employee not Exists"));

        Admin admin = loggedInUser.getLoggedInAdmin();
        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));

        String branchName = employeeDto.getBranchName();
        Branch branch = branchRepository.findByBranchNameAndBank(branchName, bank).orElseThrow(() -> new NotFoundException("Branch not Exists"));


        if (!employee.getBranch().getBank().equals(bank)) {
            throw new UnAuthorizedException("Access Denied");
        }

        String firstName = employeeDto.getFirstName();
        String lastName = employeeDto.getLastName();
        LocalDate dob = employeeDto.getDob();
        String address = employeeDto.getAddress();
        String phone = employeeDto.getPhone();
        float salary = employeeDto.getSalary();


        employee.setBranch(branch);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDob(dob);
        employee.setAddress(address);
        employee.setPhone(phone);
        employee.setSalary(salary);

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(int employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException("employee not Exists"));

        Admin admin = loggedInUser.getLoggedInAdmin();
        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));

        if (!employee.getBranch().getBank().equals(bank)) {
            throw new UnAuthorizedException("Access Denied");
        }

        employeeRepository.delete(employee);
    }


    public Customer createCustomer(CustomerDto customerDto) {

        String branchName = customerDto.getBranchName();
        Branch branch = branchRepository.findByBranchName(branchName).orElseThrow(() -> new RuntimeException("Branch not found with name: " + branchName));

        Admin admin = loggedInUser.getLoggedInAdmin();
        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));

        if (!branch.getBank().equals(bank)) {
            throw new UnAuthorizedException("Access Denied");
        }

        Customer customer = objectMapper.convertValue(customerDto, Customer.class);
        customer.setBranch(branch);

        String email = customerDto.getEmail();
        String password = customerDto.getPassword();
        Credential cred = helper.createCredential(email, password, UserRole.CUSTOMER.name());

        Credential credential = securityServiceClient.register(cred);

        customer.setCredentialId(credential.getCredentialId());

        return customerRepository.save(customer);

    }

    public Customer updateCustomerDetails(int customerId, CustomerDtoUpdate customerDtoUpdate) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not Exists"));

        Admin admin = loggedInUser.getLoggedInAdmin();

        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));

        if(!customer.getBranch().getBank().equals(bank)){
            throw new UnAuthorizedException("Access denied");
        }

        String firstName = customerDtoUpdate.getFirstName();
        String lastName = customerDtoUpdate.getLastName();
        LocalDate dob = customerDtoUpdate.getDob();
        String address = customerDtoUpdate.getAddress();
        String phone = customerDtoUpdate.getPhone();


        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setDob(dob);
        customer.setAddress(address);
        customer.setPhone(phone);

        return customerRepository.save(customer);

    }


    public void deleteCustomer(int customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not Exists"));

        Admin admin = loggedInUser.getLoggedInAdmin();

        Bank bank = bankRepository.findByAdmin(admin).orElseThrow(() -> new NotFoundException("Bank not Exists"));
        if(!customer.getBranch().getBank().equals(bank)){
            throw new UnAuthorizedException("Access denied");
        }

        customerRepository.delete(customer);

    }
}
