package in.banking.cbs.action_service.controller;

import in.banking.cbs.action_service.DTO.CustomerDto;
import in.banking.cbs.action_service.DTO.CustomerDtoUpdate;
import in.banking.cbs.action_service.entity.Customer;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.EmployeeService;
import in.banking.cbs.action_service.utility.ResponseStatus;
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
}
