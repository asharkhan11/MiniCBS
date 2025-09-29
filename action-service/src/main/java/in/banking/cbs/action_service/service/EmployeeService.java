package in.banking.cbs.action_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.banking.cbs.action_service.DTO.*;
import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.*;
import in.banking.cbs.action_service.exception.InActiveAccountException;
import in.banking.cbs.action_service.exception.InSufficientAmountException;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.exception.UnAuthorizedException;
import in.banking.cbs.action_service.repository.AccountRepository;
import in.banking.cbs.action_service.repository.BranchRepository;
import in.banking.cbs.action_service.repository.CustomerRepository;
import in.banking.cbs.action_service.security.LoggedInUser;
import in.banking.cbs.action_service.utility.AccountType;
import in.banking.cbs.action_service.utility.Helper;
import in.banking.cbs.action_service.utility.UserRole;
import in.banking.cbs.action_service.utility.UserStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final ObjectMapper objectMapper;
    private final BranchRepository branchRepository;
    private final SecurityServiceClient securityServiceClient;
    private final LoggedInUser loggedInUser;
    private final Helper helper;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public Customer createCustomer(CustomerDto customerDto) {

        String branchName = customerDto.getBranchName();
        Branch branch = branchRepository.findByBranchName(branchName).orElseThrow(() -> new RuntimeException("Branch not found with name: " + branchName));

        Employee employee = loggedInUser.getLoggedInEmployee();

        if (!employee.getBranch().equals(branch)) {
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

        if (!employee.getBranch().equals(customer.getBranch())) {
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

        if (!employee.getBranch().equals(customer.getBranch())) {
            throw new UnAuthorizedException("Access denied");
        }

        customerRepository.delete(customer);

    }

    public Account createAccount(@Valid AccountDto accountDto) {

        int customerId = accountDto.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found with id : " + customerId));

        Employee employee = loggedInUser.getLoggedInEmployee();
        if (!employee.getBranch().equals(customer.getBranch())) {
            throw new UnAuthorizedException("Access denied");
        }

        Random random = new Random();

        long accountNumber = 100000000000L + (long) (random.nextDouble() * 900000000000L);
        AccountType accountType = accountDto.getAccountType();
        double balance = accountDto.getBalance();
        String currency = accountDto.getCurrency();

        Account account = Account.builder()
                .customerId(customerId)
                .accountNumber(String.valueOf(accountNumber))
                .accountType(accountType)
                .balance(balance)
                .currency(currency)
                .build();

        customer.setStatus(UserStatus.ACTIVE);
        customerRepository.save(customer);

        return accountRepository.save(account);

    }


    public Account updateAccount(int accountNumber, AccountDtoUpdate accountDto) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new NotFoundException("Account not found"));

        int customerId = account.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));

        Employee employee = loggedInUser.getLoggedInEmployee();

        if (!employee.getBranch().equals(customer.getBranch())) {
            throw new UnAuthorizedException("Access denied");
        }

        if (accountDto.getAccountType() != null) {
            account.setAccountType(accountDto.getAccountType());
        }


        account.setBalance(accountDto.getBalance());

        if (!accountDto.getCurrency().isBlank()) {
            account.setCurrency(accountDto.getCurrency());
        }

        return accountRepository.save(account);
    }


    public void deleteAccount(int accountNumber, boolean customerAlso) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new NotFoundException("Account not found"));

        int customerId = account.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));

        Employee employee = loggedInUser.getLoggedInEmployee();

        if (!employee.getBranch().equals(customer.getBranch())) {
            throw new UnAuthorizedException("Access denied");
        }

        accountRepository.delete(account);

        if (customerAlso) {
            customerRepository.delete(customer);
        }

    }


    public Account depositMoney(int accountNumber, double money) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new NotFoundException("Account not found"));

        int customerId = account.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));

        Employee employee = loggedInUser.getLoggedInEmployee();
        if (!employee.getBranch().equals(customer.getBranch())) {
            throw new UnAuthorizedException("Access denied");
        }

        if (customer.getStatus() != UserStatus.ACTIVE) {
            throw new InActiveAccountException("Account is inactive, please activate before deposit");
        }

        account.setBalance(account.getBalance() + money);

        return accountRepository.save(account);
    }


    public Account deductMoney(int accountNumber, double money) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new NotFoundException("Account not found"));

        int customerId = account.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));

        Employee employee = loggedInUser.getLoggedInEmployee();
        if (!employee.getBranch().equals(customer.getBranch())) {
            throw new UnAuthorizedException("Access denied");
        }

        if (customer.getStatus() != UserStatus.ACTIVE) {
            throw new InActiveAccountException("Account is inactive, please activate before deposit");
        }

        double availableBalance = account.getBalance();

        if(availableBalance < money){
            throw new InSufficientAmountException("low balance : "+availableBalance);
        }

        account.setBalance(availableBalance - money);

        return accountRepository.save(account);

    }
}
