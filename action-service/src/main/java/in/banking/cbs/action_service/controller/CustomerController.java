package in.banking.cbs.action_service.controller;

import in.banking.cbs.action_service.DTO.TransferToDto;
import in.banking.cbs.action_service.entity.Transactions;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/transfer")
    public ResponseEntity<Response<Transactions>> transferTo(@RequestBody TransferToDto transferToDto){

//        Transactions transaction = customerService.transferTo(transferToDto);
return null;
    }

}
