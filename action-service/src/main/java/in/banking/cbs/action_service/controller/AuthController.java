package in.banking.cbs.action_service.controller;


import in.banking.cbs.action_service.DTO.AdminDto;
import in.banking.cbs.action_service.DTO.LoginDto;
import in.banking.cbs.action_service.DTO.LoginResponse;
import in.banking.cbs.action_service.entity.Admin;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.AuthService;
import in.banking.cbs.action_service.utility.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
