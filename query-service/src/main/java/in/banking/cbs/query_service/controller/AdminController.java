package in.banking.cbs.query_service.controller;

import in.banking.cbs.query_service.entity.Bank;
import in.banking.cbs.query_service.message.Response;
import in.banking.cbs.query_service.service.AdminService;
import in.banking.cbs.query_service.utility.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final AdminService adminService;

    @GetMapping("/{bankName}")
    public ResponseEntity<Response<Bank>> getBankByName(@PathVariable String bankName){
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

    @GetMapping("/address/{address}")
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

}
