package in.banking.cbs.action_service.controller;

import in.banking.cbs.action_service.DTO.AccountDto;
import in.banking.cbs.action_service.DTO.AccountDtoUpdate;
import in.banking.cbs.action_service.DTO.CustomerDto;
import in.banking.cbs.action_service.DTO.CustomerDtoUpdate;
import in.banking.cbs.action_service.entity.Account;
import in.banking.cbs.action_service.entity.Customer;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.EmployeeService;
import in.banking.cbs.action_service.utility.ResponseStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/customer")
    public ResponseEntity<Response<Customer>> createCustomer(@RequestBody CustomerDto customerDto) {

        Customer customer = employeeService.createCustomer(customerDto);

        Response<Customer> response = Response.<Customer>builder()
                .status(ResponseStatus.CREATED)
                .message("Customer created successfully")
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }


    @PutMapping("/customer/{customerId}")
    public ResponseEntity<Response<Customer>> updateCustomer(@PathVariable int customerId, @RequestBody CustomerDtoUpdate customerDtoUpdate) {

        Customer customer = employeeService.updateCustomerDetails(customerId, customerDtoUpdate);

        Response<Customer> response = Response.<Customer>builder()
                .status(ResponseStatus.UPDATED)
                .message("Customer updated successfully")
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Response<Void>> deleteCustomer(@PathVariable int customerId) {
        employeeService.deleteCustomer(customerId);

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.DELETED)
                .message("Customer deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/account")
    public ResponseEntity<Response<Account>> createAccount(@RequestBody @Valid AccountDto accountDto){

        Account account = employeeService.createAccount(accountDto);

        Response<Account> response = Response.<Account>builder()
                .status(ResponseStatus.CREATED)
                .message("Account created successfully")
                .data(account)
                .build();

        return ResponseEntity.ok(response);

    }

    @PutMapping("/account/{accountNumber}")
    public ResponseEntity<Response<Account>> updateAccount(@PathVariable @Positive String accountNumber, @RequestBody @Valid AccountDtoUpdate accountDto){

        Account account = employeeService.updateAccount(accountNumber, accountDto);

        Response<Account> response = Response.<Account>builder()
                .status(ResponseStatus.UPDATED)
                .message("Account updated successfully")
                .data(account)
                .build();

        return ResponseEntity.ok(response);

    }


    @DeleteMapping("/account/{accountNumber}/{customerAlso}")
    public ResponseEntity<Response<Void>> deleteAccount(@PathVariable String accountNumber, @PathVariable boolean customerAlso) {

        employeeService.deleteAccount(accountNumber, customerAlso);

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.DELETED)
                .message("Account deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/account/{accountNumber}/deposit")
    public ResponseEntity<Response<Account>> depositMoney(@PathVariable String accountNumber, @RequestParam double money){

        Account account = employeeService.depositMoney(accountNumber, money);

        Response<Account> response = Response.<Account>builder()
                .status(ResponseStatus.SUCCESS)
                .message("Money deposited successfully")
                .data(account)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/account/{accountNumber}/deduct")
    public ResponseEntity<Response<Account>> deductMoney(@PathVariable String accountNumber, @RequestParam double money){

        Account account = employeeService.deductMoney(accountNumber, money);

        Response<Account> response = Response.<Account>builder()
                .status(ResponseStatus.SUCCESS)
                .message("Money deducted successfully")
                .data(account)
                .build();

        return ResponseEntity.ok(response);
    }


}
