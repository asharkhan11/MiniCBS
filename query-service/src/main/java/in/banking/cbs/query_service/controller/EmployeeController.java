package in.banking.cbs.query_service.controller;

import in.banking.cbs.query_service.entity.Branch;
import in.banking.cbs.query_service.entity.Employee;
import in.banking.cbs.query_service.message.Response;
import in.banking.cbs.query_service.service.EmployeeService;
import in.banking.cbs.query_service.utility.ResponseStatus;
import in.banking.cbs.query_service.utility.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<Response<Employee>> getEmployeeById(@PathVariable int employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        Response<Employee> response = Response.<Employee>builder()
                .status(ResponseStatus.FOUND)
                .message("Employee retrieved successfully")
                .data(employee)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<Employee>>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        Response<List<Employee>> response = Response.<List<Employee>>builder()
                .status(ResponseStatus.FOUND)
                .message("All employees retrieved successfully")
                .data(employees)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<Response<List<Employee>>> getEmployeesByFirstName(@PathVariable String firstName) {
        List<Employee> employees = employeeService.getEmployeesByFirstName(firstName);
        Response<List<Employee>> response = Response.<List<Employee>>builder()
                .status(ResponseStatus.FOUND)
                .message("Employees retrieved successfully")
                .data(employees)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/salary/{salary}")
    public ResponseEntity<Response<List<Employee>>> getEmployeesBySalary(@PathVariable float salary) {
        List<Employee> employees = employeeService.getEmployeesBySalary(salary);
        Response<List<Employee>> response = Response.<List<Employee>>builder()
                .status(ResponseStatus.FOUND)
                .message("Employees retrieved successfully")
                .data(employees)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dob/{dob}")
    public ResponseEntity<Response<List<Employee>>> getEmployeesByDob(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob) {
        List<Employee> employees = employeeService.getEmployeesByDob(dob);
        Response<List<Employee>> response = Response.<List<Employee>>builder()
                .status(ResponseStatus.FOUND)
                .message("Employees retrieved successfully")
                .data(employees)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Response<Employee>> getEmployeesByPhone(@PathVariable String phone) {
        Employee employees = employeeService.getEmployeesByPhone(phone);
        Response<Employee> response = Response.<Employee>builder()
                .status(ResponseStatus.FOUND)
                .message("Employee retrieved successfully")
                .data(employees)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Response<List<Employee>>> getEmployeesByStatus(@PathVariable UserStatus status) {
        List<Employee> employees = employeeService.getEmployeesByStatus(status);
        Response<List<Employee>> response = Response.<List<Employee>>builder()
                .status(ResponseStatus.FOUND)
                .message("Employees retrieved successfully")
                .data(employees)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<Response<List<Employee>>> getEmployeesByBranch(@PathVariable int branchId) {
        Branch branch = new Branch();
        branch.setBranchId(branchId);
        List<Employee> employees = employeeService.getEmployeesByBranch(branch);
        Response<List<Employee>> response = Response.<List<Employee>>builder()
                .status(ResponseStatus.FOUND)
                .message("Employees retrieved successfully")
                .data(employees)
                .build();
        return ResponseEntity.ok(response);
    }
}
