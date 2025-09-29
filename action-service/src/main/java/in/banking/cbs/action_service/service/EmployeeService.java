package in.banking.cbs.action_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.banking.cbs.action_service.DTO.Credential;
import in.banking.cbs.action_service.DTO.CustomerDto;
import in.banking.cbs.action_service.DTO.CustomerDtoUpdate;
import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.*;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.exception.UnAuthorizedException;
import in.banking.cbs.action_service.repository.BranchRepository;
import in.banking.cbs.action_service.repository.CustomerRepository;
import in.banking.cbs.action_service.security.LoggedInUser;
import in.banking.cbs.action_service.utility.Helper;
import in.banking.cbs.action_service.utility.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final ObjectMapper objectMapper;
    private final BranchRepository branchRepository;
    private final SecurityServiceClient securityServiceClient;
    private final LoggedInUser loggedInUser;
    private final Helper helper;
    private final CustomerRepository customerRepository;

    public Customer createCustomer(CustomerDto customerDto) {

        String branchName = customerDto.getBranchName();
        Branch branch = branchRepository.findByBranchName(branchName).orElseThrow(() -> new RuntimeException("Branch not found with name: " + branchName));

        Employee employee = loggedInUser.getLoggedInEmployee();

        if(!employee.getBranch().equals(branch)){
            throw new UnAuthorizedException("Access denied");
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

        Employee employee = loggedInUser.getLoggedInEmployee();

        if(!employee.getBranch().equals(customer.getBranch())){
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

        Employee employee = loggedInUser.getLoggedInEmployee();

        if(!employee.getBranch().equals(customer.getBranch())){
            throw new UnAuthorizedException("Access denied");
        }

        customerRepository.delete(customer);

    }

}
