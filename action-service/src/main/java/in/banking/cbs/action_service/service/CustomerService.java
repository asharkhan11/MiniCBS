package in.banking.cbs.action_service.service;


import in.banking.cbs.action_service.DTO.LoanRequestDTO;
import in.banking.cbs.action_service.DTO.TransferToDto;
import in.banking.cbs.action_service.client.SecurityServiceClient;
import in.banking.cbs.action_service.entity.*;
import in.banking.cbs.action_service.exception.*;
import in.banking.cbs.action_service.repository.*;
import in.banking.cbs.action_service.security.LoggedInUser;
import in.banking.cbs.action_service.utility.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final LoggedInUser loggedInUser;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final LoanRequestRepository loanRequestRepository;


    @Transactional
    public Transactions transferTo(TransferToDto transferToDto) {
        String toAccountNumber = transferToDto.getToAccountNumber();
        double amount = transferToDto.getAmount();
        String remarks = transferToDto.getRemarks();

        Customer customer = loggedInUser.getLoggedInCustomer();
        Branch branch = customer.getBranch();
        int customerId = customer.getCustomerId();
        Account fromAccount = accountRepository.findByCustomerId(customerId).orElseThrow(() -> new NotFoundException("Account not found"));

        String fromAccountNumber = fromAccount.getAccountNumber();
        int branchId = branch.getBranchId();

        TxnType txnType = TxnType.TRANSFER;
        String currency = fromAccount.getCurrency();
        String referenceNo = UUID.randomUUID().toString();

        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber).orElseThrow(() -> new NotFoundException("Invalid account number of receiver"));
        int toAccountCustomerId = toAccount.getCustomerId();
        Customer toAccountCustomer = customerRepository.findById(toAccountCustomerId).orElseThrow(()-> new NotFoundException("receiver customer not exists"));

        if(!toAccount.getCurrency().equals(fromAccount.getCurrency())){
            throw new InvalidDataException("Currency of receiver's account does not match");
        }

        if(fromAccount.getBalance() < amount){
            throw new InSufficientAmountException("low balance : "+fromAccount.getBalance());
        }

        if(fromAccount.getStatus() != UserStatus.ACTIVE || toAccount.getStatus() !=  UserStatus.ACTIVE){
            throw new InActiveAccountException("Either of the bank account is InActive, please visit the branch for activation");
        }

        transferMoney(fromAccount, toAccount, amount);


        Transactions debitTxn = Transactions.builder()
                .branchId(branchId)
                .customerId(customerId)
                .fromAccountNumber(fromAccountNumber)
                .toAccountNumber(toAccountNumber)
                .txnType(txnType)
                .amount(amount)
                .currency(currency)
                .status(TxnStatus.SUCCESS)
                .remarks(remarks)
                .referenceNo(referenceNo)
                .entryType(EntryType.DEBIT)
                .build();


        Transactions creditTxn = Transactions.builder()
                .branchId(toAccountCustomer.getBranch().getBranchId())
                .customerId(toAccountCustomerId)
                .fromAccountNumber(fromAccountNumber)
                .toAccountNumber(toAccountNumber)
                .txnType(txnType)
                .amount(amount)
                .currency(currency)
                .status(TxnStatus.SUCCESS)
                .remarks(remarks)
                .referenceNo(referenceNo)
                .entryType(EntryType.CREDIT)
                .build();


        List<Transactions> transactions = transactionRepository.saveAll(List.of(creditTxn, debitTxn));

        return transactions.get(1);
    }


    private void transferMoney(Account fromAccount, Account toAccount, double amount) {

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.saveAll(List.of(fromAccount,toAccount));

    }

    public int requestLoan(@Valid LoanRequestDTO loanRequestDTO) {

        Customer customer = loggedInUser.getLoggedInCustomer();

//        loanRequestRepository.findByCustomerId(customer.getCustomerId()).ifPresent(loanRequest->{
//            throw new AlreadyExistsException("loan request already exists with request id : "+ loanRequest.getRequestId());
//        });

        LoanRequest loanRequest = LoanRequest.builder()
                .amount(loanRequestDTO.getAmount())
                .termInMonths(loanRequestDTO.getTermInMonths())
                .customerId(customer.getCustomerId())
                .branchId(customer.getBranch().getBranchId())
                .purpose(loanRequestDTO.getPurpose())
                .build();

        return loanRequestRepository.save(loanRequest).getRequestId();

    }

    public LoanRequestStatus checkLoanStatus() {

        Customer customer = loggedInUser.getLoggedInCustomer();

        LoanRequest loanRequest = loanRequestRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new NotFoundException("Loan request not found"));

        return loanRequest.getStatus();

    }
}
