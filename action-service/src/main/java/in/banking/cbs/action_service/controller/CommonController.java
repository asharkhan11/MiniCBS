package in.banking.cbs.action_service.controller;

import in.banking.cbs.action_service.DTO.ChangePasswordDto;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.CommonService;
import in.banking.cbs.action_service.utility.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/credential")
@RestController
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @PutMapping("/change-password")
    public ResponseEntity<Response<Void>> changePassword(@RequestBody ChangePasswordDto changePasswordDto){

        commonService.changePassword(changePasswordDto);

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.UPDATED)
                .message("Password Changed successfully")
                .build();

        return ResponseEntity.ok(response);

    }



}
