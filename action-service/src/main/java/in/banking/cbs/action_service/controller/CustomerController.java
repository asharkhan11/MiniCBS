package in.banking.cbs.action_service.controller;

import in.banking.cbs.action_service.DTO.ChangePasswordDto;
import in.banking.cbs.action_service.DTO.TransferToDto;
import in.banking.cbs.action_service.configuration.ThreadPoolTaskExecutorConfig;
import in.banking.cbs.action_service.entity.Transactions;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.security.LoggedInUser;
import in.banking.cbs.action_service.service.CustomerService;
import in.banking.cbs.action_service.service.MinioClientService;
import in.banking.cbs.action_service.utility.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final MinioClientService minioClientService;
    private final LoggedInUser loggedInUser;
    private final TaskExecutor executor;

    @PostMapping("/transfer")
    public ResponseEntity<Response<Transactions>> transferTo(@RequestBody TransferToDto transferToDto){

        Transactions transaction = customerService.transferTo(transferToDto);

        Response<Transactions> response = Response.<Transactions>builder()
                .status(ResponseStatus.UPDATED)
                .message("Customer updated successfully")
                .data(transaction)
                .build();

        return ResponseEntity.ok(response);

    }


//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<List<String>> uploadDocumentsForKyc(@RequestPart("files") List<MultipartFile> files) {
//        int customerId = loggedInUser.getLoggedInCustomer().getCustomerId();
//
//        files.forEach(f->{
//            executor.execute(()->{
//                String filePath = minioClientService.sendFileToMinio(f);
//
//            });
//        });
//
//    }



    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName) {
        byte[] data = minioClientService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

}
