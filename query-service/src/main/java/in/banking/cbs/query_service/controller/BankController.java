package in.banking.cbs.query_service.controller;

import in.banking.cbs.query_service.entity.Bank;
import in.banking.cbs.query_service.message.Response;
import in.banking.cbs.query_service.service.BankService;
import in.banking.cbs.query_service.utility.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping("/{bankName}")
    public ResponseEntity<Response<Bank>> getBankByName(@PathVariable String bankName){
        Bank bankByName = bankService.getBankByName(bankName);

        Response<Bank> response = Response.<Bank>builder()
                .status(ResponseStatus.FETCHED)
                .message("Bank fetched Successfully")
                .data(bankByName)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Response<Bank>> getBankByEmail(@PathVariable String email) {
        Bank bankByEmail = bankService.getBankByEmail(email);

        Response<Bank> response = Response.<Bank>builder()
                .status(ResponseStatus.FOUND)
                .message("Bank fetched successfully")
                .data(bankByEmail)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<Response<List<Bank>>> getBanksByAddress(@PathVariable String address) {
        List<Bank> banks = bankService.getBanksByAddress(address);

        Response<List<Bank>> response = Response.<List<Bank>>builder()
                .status(ResponseStatus.FETCHED)
                .message("Banks fetched successfully")
                .data(banks)
                .build();

        return ResponseEntity.ok(response);
    }


}
