package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.DTO.LoanAccountDto;
import in.banking.cbs.action_service.entity.Account;
import in.banking.cbs.action_service.entity.Employee;
import in.banking.cbs.action_service.entity.LoanRequest;
import in.banking.cbs.action_service.exception.InvalidOperationException;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.exception.UnAuthorizedException;
import in.banking.cbs.action_service.repository.AccountRepository;
import in.banking.cbs.action_service.repository.LoanRequestRepository;
import in.banking.cbs.action_service.security.LoggedInUser;
import in.banking.cbs.action_service.utility.AccountType;
import in.banking.cbs.action_service.utility.LoanRequestStatus;
import in.banking.cbs.action_service.utility.UserStatus;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final LoanRequestRepository loanRequestRepository;
    private final LoggedInUser loggedInUser;
    private final AccountRepository accountRepository;

    public LoanRequest approveLoanRequest(@Positive int requestId) {

        Employee manager = loggedInUser.getLoggedInEmployee();

        LoanRequest loanRequest = loanRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("loan request not found with id : " + requestId));

        if (loanRequest.getBranchId() != manager.getBranch().getBranchId()){
            throw new UnAuthorizedException("Invalid request id : "+requestId);
        }

        if(loanRequest.getStatus() == LoanRequestStatus.PENDING){
            loanRequest.setStatus(LoanRequestStatus.APPROVED);
            return loanRequestRepository.save(loanRequest);
        } else if (loanRequest.getStatus() == LoanRequestStatus.APPROVED) {
            return loanRequest;
        }else {
            throw new InvalidOperationException("Loan request cannot be approved as it is already REJECTED");
        }
    }


    public LoanRequest rejectLoanRequest(@Positive int requestId) {

        Employee manager = loggedInUser.getLoggedInEmployee();

        LoanRequest loanRequest = loanRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("loan request not found with id : " + requestId));

        if (loanRequest.getBranchId() != manager.getBranch().getBranchId()){
            throw new UnAuthorizedException("Invalid request id : "+requestId);
        }

        if(loanRequest.getStatus() == LoanRequestStatus.PENDING){
            loanRequest.setStatus(LoanRequestStatus.REJECTED);
            return loanRequestRepository.save(loanRequest);
        } else if (loanRequest.getStatus() == LoanRequestStatus.REJECTED) {
            return loanRequest;
        }else {
            throw new InvalidOperationException("Loan request cannot be rejected as it is already APPROVED");
        }
    }

    public Account createLoanAccount(LoanAccountDto loanAccountDto) {

        int requestId = loanAccountDto.getLoanRequestId();

        Employee manager = loggedInUser.getLoggedInEmployee();

        LoanRequest loanRequest = loanRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("loan request not found with id : " + requestId));

        if (loanRequest.getBranchId() != manager.getBranch().getBranchId()){
            throw new UnAuthorizedException("Invalid request id : "+requestId);
        }

        if(loanRequest.getStatus() != LoanRequestStatus.APPROVED){
            throw new UnAuthorizedException("Loan request must be APPROVED first");
        }

        Random random = new Random();
        long accountNumber = 100000000000L + (long) (random.nextDouble() * 900000000000L);

        Account account = Account.builder()
                .customerId(loanRequest.getCustomerId())
                .accountNumber(String.valueOf(accountNumber))
                .accountType(AccountType.LOAN)
                .balance(loanRequest.getAmount())
                .currency(loanAccountDto.getCurrency())
                .status(UserStatus.ACTIVE)
                .kyc(loanAccountDto.getKyc())
                .build();

        return accountRepository.save(account);

    }



    /// /////////// QUERY SERVICE ////////////////////


    public List<LoanRequest> getLoanRequestsByStatus(LoanRequestStatus status) {

        Employee manager = loggedInUser.getLoggedInEmployee();

        int branchId = manager.getBranch().getBranchId();

        return loanRequestRepository.findAllByBranchIdAndStatus(branchId, status);

    }


}
