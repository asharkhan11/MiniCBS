package in.banking.cbs.action_service.controller;

import in.banking.cbs.action_service.DTO.LoanAccountDto;
import in.banking.cbs.action_service.entity.Account;
import in.banking.cbs.action_service.entity.LoanRequest;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.ManagerService;
import in.banking.cbs.action_service.utility.LoanRequestStatus;
import in.banking.cbs.action_service.utility.ResponseStatus;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/loan/approve/{requestId}")
    public ResponseEntity<Response<LoanRequest>> approveLoanRequest(@PathVariable @Positive int requestId){

        LoanRequest loanRequest = managerService.approveLoanRequest(requestId);

        Response<LoanRequest> response = Response.<LoanRequest>builder()
                .status(ResponseStatus.SUCCESS)
                .message("Loan request APPROVED successfully")
                .data(loanRequest)
                .build();

        return ResponseEntity.ok(response);

    }

    @PostMapping("/loan/reject/{requestId}")
    public ResponseEntity<Response<LoanRequest>> rejectLoanRequest(@PathVariable @Positive int requestId){

        LoanRequest loanRequest = managerService.rejectLoanRequest(requestId);

        Response<LoanRequest> response = Response.<LoanRequest>builder()
                .status(ResponseStatus.SUCCESS)
                .message("Loan request REJECTED successfully")
                .data(loanRequest)
                .build();

        return ResponseEntity.ok(response);

    }

    @PostMapping("/loan/account")
    public ResponseEntity<Response<Account>> createLoanAccount(@RequestBody LoanAccountDto loanAccountDto) {

        Account account = managerService.createLoanAccount(loanAccountDto);

        Response<Account> response = Response.<Account>builder()
                .status(ResponseStatus.CREATED)
                .message("Loan Account created successfully")
                .data(account)
                .build();

        return ResponseEntity.ok(response);

    }


    /// ///////////////// QUERY SERVICE /////////////

    @GetMapping("/loan/status/{status}")
    public ResponseEntity<Response<List<LoanRequest>>> getLoanRequestsByStatus(@PathVariable LoanRequestStatus status){

        List<LoanRequest> loanRequests = managerService.getLoanRequestsByStatus(status);

        Response<List<LoanRequest>> response = Response.<List<LoanRequest>>builder()
                .status(ResponseStatus.SUCCESS)
                .message("Loan requests fetched with status : '"+status + "'")
                .data(loanRequests)
                .build();

        return ResponseEntity.ok(response);

    }


}
