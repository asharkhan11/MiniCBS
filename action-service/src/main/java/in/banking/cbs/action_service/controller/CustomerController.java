package in.banking.cbs.action_service.controller;

import in.banking.cbs.action_service.DTO.LoanRequestDTO;
import in.banking.cbs.action_service.DTO.TransferToDto;
import in.banking.cbs.action_service.entity.CustomerDocument;
import in.banking.cbs.action_service.entity.Transactions;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.repository.CustomerDocumentRepository;
import in.banking.cbs.action_service.security.LoggedInUser;
import in.banking.cbs.action_service.service.CustomerService;
import in.banking.cbs.action_service.service.MinioClientService;
import in.banking.cbs.action_service.utility.LoanRequestStatus;
import in.banking.cbs.action_service.utility.ResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final MinioClientService minioClientService;
    private final LoggedInUser loggedInUser;
    private final CustomerDocumentRepository documentRepository;


    @PostMapping("/transfer")
    public ResponseEntity<Response<Transactions>> transferTo(@RequestBody TransferToDto transferToDto) {

        Transactions transaction = customerService.transferTo(transferToDto);

        Response<Transactions> response = Response.<Transactions>builder()
                .status(ResponseStatus.UPDATED)
                .message("Customer updated successfully")
                .data(transaction)
                .build();

        return ResponseEntity.ok(response);

    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Flux<String>> uploadDocumentsForKyc(@RequestPart("files") List<MultipartFile> files) {
        int customerId = loggedInUser.getLoggedInCustomer().getCustomerId();

        Flux<String> resultFlux = Flux.fromIterable(files)
                .flatMap(file -> minioClientService.sendFileToMinio(file)
                        .flatMap(path -> {
                            CustomerDocument doc = CustomerDocument.builder()
                                    .minioFilePath(path)
                                    .customerId(customerId)
                                    .build();
                            return Mono.fromCallable(() -> documentRepository.save(doc))
                                    .thenReturn(path);
                        })
                );

        return ResponseEntity.ok(resultFlux);
    }


    @PostMapping("/loan/request")
    public ResponseEntity<Response<String>> requestLoan(@Valid @RequestBody LoanRequestDTO loanRequest) {

        int requestId = customerService.requestLoan(loanRequest);

        Response<String> response = Response.<String>builder()
                .status(ResponseStatus.SUCCESS)
                .message("Loan request submitted successfully")
                .data("kindly check your mail for updates. use this request id for future reference : " + requestId)
                .build();

        return ResponseEntity.ok(response);
    }


    /// ///////////////// QUERY SERVICE /////////////

    @GetMapping("/loan/status")
    public ResponseEntity<Response<LoanRequestStatus>> checkLoanStatus(){
        LoanRequestStatus status =  customerService.checkLoanStatus();


        Response<LoanRequestStatus> response = Response.<LoanRequestStatus>builder()
                .status(ResponseStatus.SUCCESS)
                .message("Loan status fetched successfully")
                .data(status)
                .build();

        return ResponseEntity.ok(response);
    }

}
