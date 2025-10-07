package in.banking.cbs.query_service.controller;

import in.banking.cbs.query_service.DTO.AdminResponseDto;
import in.banking.cbs.query_service.DTO.Credential;
import in.banking.cbs.query_service.DTO.Roles;
import in.banking.cbs.query_service.client.SecurityServiceClient;
import in.banking.cbs.query_service.entity.Admin;
import in.banking.cbs.query_service.entity.Bank;
import in.banking.cbs.query_service.entity.Branch;
import in.banking.cbs.query_service.entity.Employee;
import in.banking.cbs.query_service.message.Response;
import in.banking.cbs.query_service.security.LoggedInUser;
import in.banking.cbs.query_service.service.AdminService;
import in.banking.cbs.query_service.utility.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final LoggedInUser loggedInUser;
    private final SecurityServiceClient securityServiceClient;

    //Bank
    @GetMapping("/bank/{bankName}")
    public ResponseEntity<Response<Bank>> getBankByName(@PathVariable String bankName) {
        Bank bankByName = adminService.getBankByName(bankName);

        Response<Bank> response = Response.<Bank>builder()
                .status(in.banking.cbs.query_service.utility.ResponseStatus.FETCHED)
                .message("Bank fetched Successfully")
                .data(bankByName)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Response<Bank>> getBankByEmail(@PathVariable String email) {
        Bank bankByEmail = adminService.getBankByEmail(email);

        Response<Bank> response = Response.<Bank>builder()
                .status(in.banking.cbs.query_service.utility.ResponseStatus.FOUND)
                .message("Bank fetched successfully")
                .data(bankByEmail)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/bank/address/{address}")
    public ResponseEntity<Response<List<Bank>>> getBanksByAddress(@PathVariable String address) {
        List<Bank> banks = adminService.getBanksByAddress(address);

        Response<List<Bank>> response = Response.<List<Bank>>builder()
                .status(in.banking.cbs.query_service.utility.ResponseStatus.FETCHED)
                .message("Banks fetched successfully")
                .data(banks)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/address/{keyword}")
    public ResponseEntity<Response<List<Bank>>> getBanksByAddressKeyword(@PathVariable String keyword) {
        List<Bank> banks = adminService.getBanksByAddressKeyword(keyword);

        Response<List<Bank>> response = Response.<List<Bank>>builder()
                .status(ResponseStatus.FETCHED)
                .message("Banks fetched successfully by keyword")
                .data(banks)
                .build();

        return ResponseEntity.ok(response);
    }

    //Branch
    @GetMapping("/branchId/{branchId}")
    public ResponseEntity<Response<Branch>> getBranchById(@PathVariable int branchId) {
        Branch branch = adminService.getBranchById(branchId);
        Response<Branch> response = Response.<Branch>builder()
                .status(ResponseStatus.FOUND)
                .message("Branch retrieved successfully")
                .data(branch)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branchName/{branchName}")
    public ResponseEntity<Response<Branch>> getBranchByName(@PathVariable String branchName) {
        Branch branch = adminService.getBranchByName(branchName);
        Response<Branch> response = Response.<Branch>builder()
                .status(ResponseStatus.FOUND)
                .message("Branch retrieved successfully")
                .data(branch)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ifsc/{ifscCode}")
    public ResponseEntity<Response<Branch>> getBranchByIfsc(@PathVariable String ifscCode) {
        Branch branch = adminService.getBranchByIfsc(ifscCode);
        Response<Branch> response = Response.<Branch>builder()
                .status(ResponseStatus.FOUND)
                .message("Branch retrieved successfully")
                .data(branch)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch")
    public ResponseEntity<Response<List<Branch>>> getAllBranches() {
        List<Branch> branches = adminService.getAllBranches();
        Response<List<Branch>> response = Response.<List<Branch>>builder()
                .status(ResponseStatus.FOUND)
                .message("All branches retrieved successfully")
                .data(branches)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/city/{city}")
    public ResponseEntity<Response<List<Branch>>> getBranchesByCity(@PathVariable String city) {
        List<Branch> branches = adminService.getBranchesByCity(city);
        Response<List<Branch>> response = Response.<List<Branch>>builder()
                .status(ResponseStatus.FOUND)
                .message("Branches retrieved successfully")
                .data(branches)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/state/{state}")
    public ResponseEntity<Response<List<Branch>>> getBranchesByState(@PathVariable String state) {
        List<Branch> branches = adminService.getBranchesByState(state);
        Response<List<Branch>> response = Response.<List<Branch>>builder()
                .status(ResponseStatus.FOUND)
                .message("Branches retrieved successfully")
                .data(branches)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/address/{address}")
    public ResponseEntity<Response<List<Branch>>> getBranchesByAddress(@PathVariable String address) {
        List<Branch> branches = adminService.getBranchesByAddress(address);
        Response<List<Branch>> response = Response.<List<Branch>>builder()
                .status(ResponseStatus.FOUND)
                .message("Branches retrieved successfully")
                .data(branches)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/contact/{contactNumber}")
    public ResponseEntity<Response<Branch>> getBranchByContactNumber(@PathVariable String contactNumber) {
        Branch branch = adminService.getBranchByContactNumber(contactNumber);
        Response<Branch> response = Response.<Branch>builder()
                .status(ResponseStatus.FOUND)
                .message("Branch retrieved successfully")
                .data(branch)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/manager/{managerId}")
    public ResponseEntity<Response<List<Branch>>> getBranchesByManager(@PathVariable int managerId) {
        Employee manager = new Employee();
        manager.setEmployeeId(managerId);
        List<Branch> branches = adminService.getBranchesByManager(manager);
        Response<List<Branch>> response = Response.<List<Branch>>builder()
                .status(ResponseStatus.FOUND)
                .message("Branches retrieved successfully")
                .data(branches)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bank/{bankId}")
    public ResponseEntity<Response<List<Branch>>> getBranchesByBank(@PathVariable int bankId) {
        Bank bank = new Bank();
        bank.setBankId(bankId);
        List<Branch> branches = adminService.getBranchesByBank(bank);
        Response<List<Branch>> response = Response.<List<Branch>>builder()
                .status(ResponseStatus.FOUND)
                .message("Branches retrieved successfully")
                .data(branches)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/adminDetail")
    public ResponseEntity<Response<AdminResponseDto>> getMyDetails() {
        AdminResponseDto adminResponseDto = adminService.getMyDetails();
        Response<AdminResponseDto> response = Response.<AdminResponseDto>builder()
                .status(ResponseStatus.CREATED)
                .message("Bank created successfully")
                .data(adminResponseDto)
                .build();

        return ResponseEntity.ok(response);
    }
}