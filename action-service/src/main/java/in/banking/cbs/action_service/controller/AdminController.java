package in.banking.cbs.action_service.controller;


import in.banking.cbs.action_service.DTO.*;
import in.banking.cbs.action_service.entity.Bank;
import in.banking.cbs.action_service.entity.Branch;
import in.banking.cbs.action_service.entity.Customer;
import in.banking.cbs.action_service.entity.Employee;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.AdminService;
import in.banking.cbs.action_service.utility.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/bank")
    public ResponseEntity<Response<Bank>> createBank(@RequestBody BankDto bankDto) {

        Bank bank = adminService.createBank(bankDto);

        Response<Bank> response = Response.<Bank>builder()
                .status(ResponseStatus.CREATED)
                .message("Bank created successfully")
                .data(bank)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/bank")
    public ResponseEntity<Response<Bank>> updateBank(@RequestBody BankDto bankDto) {

        Bank updatedBank = adminService.updateBank(bankDto);

        Response<Bank> response = Response.<Bank>builder()
                .status(ResponseStatus.UPDATED)
                .message("Bank Updated Successfully")
                .data(updatedBank)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/bank")
    public ResponseEntity<Response<Void>> deleteBank() {

        adminService.deleteBank();

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.DELETED)
                .message("Bank Deleted Successfully")
                .build();

        return ResponseEntity.ok(response);
    }


    @PostMapping("/branch")
    public ResponseEntity<Response<Branch>> createBranch(@RequestBody BranchDto branchDto) {

        Branch branch = adminService.createBranch(branchDto);

        Response<Branch> response = Response.<Branch>builder()
                .status(ResponseStatus.CREATED)
                .message("branch created successfully")
                .data(branch)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/branch/{branchName}")
    public ResponseEntity<Response<Branch>> updateBranch(@PathVariable String branchName, @RequestBody BranchDtoUpdate branchDtoUpdate) {
        Branch updatedBranch = adminService.updateBranch(branchName, branchDtoUpdate);

        Response<Branch> response = Response.<Branch>builder()
                .status(ResponseStatus.UPDATED)
                .message("Branch updated successfully")
                .data(updatedBranch)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/branch/{branchName}")
    public ResponseEntity<Response<Void>> deleteBranch(@PathVariable String branchName) {
        adminService.deleteBranch(branchName);

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.DELETED)
                .message("Branch deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }


    @PostMapping("/employee")
    public ResponseEntity<Response<Employee>> createEmployee(@RequestBody EmployeeDto employeeDto) {

        Employee employee = adminService.createEmployee(employeeDto);

        Response<Employee> response = Response.<Employee>builder()
                .status(ResponseStatus.CREATED)
                .message("Employee created successfully")
                .data(employee)
                .build();

        return ResponseEntity.ok(response);
    }


    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<Response<Employee>> updateEmployee(@PathVariable int employeeId, @RequestBody EmployeeDtoUpdate employeeDto) {

        Employee employee = adminService.updateEmployee(employeeId, employeeDto);

        Response<Employee> response = Response.<Employee>builder()
                .status(ResponseStatus.UPDATED)
                .message("Employee updated successfully")
                .data(employee)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<Response<Void>> deleteEmployee(@PathVariable int employeeId) {
        adminService.deleteEmployee(employeeId);

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.DELETED)
                .message("Employee deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }



    @PostMapping("/customer")
    public ResponseEntity<Response<Customer>> createCustomer(@RequestBody CustomerDto customerDto) {

        Customer customer = adminService.createCustomer(customerDto);

        Response<Customer> response = Response.<Customer>builder()
                .status(ResponseStatus.CREATED)
                .message("Customer created successfully")
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }


    @PutMapping("/customer/{customerId}")
    public ResponseEntity<Response<Customer>> updateCustomer(@PathVariable int customerId, @RequestBody CustomerDtoUpdate customerDtoUpdate) {

        Customer customer = adminService.updateCustomerDetails(customerId, customerDtoUpdate);

        Response<Customer> response = Response.<Customer>builder()
                .status(ResponseStatus.UPDATED)
                .message("Customer updated successfully")
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Response<Void>> deleteCustomer(@PathVariable int customerId) {
        adminService.deleteCustomer(customerId);

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.DELETED)
                .message("Employee deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }

}
