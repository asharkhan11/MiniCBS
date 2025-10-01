package in.banking.cbs.action_service.controller;


import in.banking.cbs.action_service.DTO.AdminDto;
import in.banking.cbs.action_service.DTO.LoginDto;
import in.banking.cbs.action_service.DTO.LoginResponse;
import in.banking.cbs.action_service.entity.Admin;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.AuthService;
import in.banking.cbs.action_service.utility.ResponseStatus;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response<Admin>> register(@RequestBody AdminDto adminDto) {

        Admin admin = authService.register(adminDto);

        Response<Admin> response = Response.<Admin>builder()
                .status(ResponseStatus.CREATED)
                .message("Admin created successfully")
                .data(admin)
                .build();

        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(@RequestBody LoginDto loginDto) {

        LoginResponse login = authService.login(loginDto);

        Response<LoginResponse> response = Response.<LoginResponse>builder()
                .status(ResponseStatus.CREATED)
                .message("Logged in successfully")
                .data(login)
                .build();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/reset-password")
    public ResponseEntity<Response<Void>> resetPassword(@RequestParam @Email String email){

        authService.sendResetPasswordMail(email);

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.SUCCESS)
                .message("OTP has been sent to your registered email successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/change-password")
    public ResponseEntity<Response<Void>> changePassword(@RequestParam int otp, @RequestParam String newPassword){

        authService.changePassword(otp, newPassword);

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.SUCCESS)
                .message("Password changed successfully")
                .build();

        return ResponseEntity.ok(response);
    }

}
