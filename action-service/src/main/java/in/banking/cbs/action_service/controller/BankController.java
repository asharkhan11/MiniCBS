package in.banking.cbs.action_service.controller;


import in.banking.cbs.action_service.DTO.BankDto;
import in.banking.cbs.action_service.entity.Bank;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.BankService;
import in.banking.cbs.action_service.utility.MapObject;
import in.banking.cbs.action_service.utility.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {


    @Autowired
    private BankService bankService;

    @PostMapping("/addBank")
    public ResponseEntity<Response<Bank>> createBank(@RequestBody BankDto bankDto) {

        Bank bank = bankService.createBank(bankDto);

        Response<Bank> response = Response.<Bank>builder()
                .status(ResponseStatus.CREATED)
                .message("Bank created successfully")
                .data(bank)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bankId}")
    public ResponseEntity<Response<Bank>> updateBank(@PathVariable int bankId, @RequestBody BankDto bankDto) {
        Bank updatedBank = bankService.updateBank(bankId,bankDto);
        Response<Bank> response = Response.<Bank>builder()
                .status(ResponseStatus.UPDATED)
                .message("Bank Updated Successfully")
                .data(updatedBank)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bankId}")
    public ResponseEntity<Response<Void>> deleteBank(@PathVariable int bankId) {
        bankService.deleteBank(bankId);
        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.DELETED)
                .message("Bank Deleted Successfully")
                .build();
        return ResponseEntity.ok(response);
    }

}
