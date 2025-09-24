package in.banking.cbs.action_service.controller;

import in.banking.cbs.action_service.DTO.UserDto;
import in.banking.cbs.action_service.DTO.UserDtoUpdateBasic;
import in.banking.cbs.action_service.DTO.UserDtoUpdateCredentials;
import in.banking.cbs.action_service.entity.User;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.UserService;
import in.banking.cbs.action_service.utility.MapObject;
import in.banking.cbs.action_service.utility.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MapObject mapper;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Response<User>> createUser(@RequestBody UserDto userDto){

        User user = userService.createUser(userDto);

        Response<User> response = Response.<User>builder()
                .status(ResponseStatus.CREATED)
                .message("user created successfully")
                .data(user)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/basic-details/{userId}")
    public ResponseEntity<Response<User>> updateUserBasicDetails(@PathVariable int userId, @RequestBody UserDtoUpdateBasic userDto){

        User user = userService.updateUserBasicDetailsById(userId, userDto);

        Response<User> response = Response.<User>builder()
                .status(ResponseStatus.UPDATED)
                .message("user's basic details updated successfully")
                .data(user)
                .build();

        return ResponseEntity.ok(response);
    }



    @PutMapping("/credentials/{userId}")
    public ResponseEntity<Response<User>> updateUserCredentials(@PathVariable int userId, @RequestBody UserDtoUpdateCredentials userDto){

        User user = userService.updateUserCredentialsById(userId, userDto);

        Response<User> response = Response.<User>builder()
                .status(ResponseStatus.UPDATED)
                .message("user's credentials updated successfully")
                .data(user)
                .build();

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Response<Void>> deleteUser(@PathVariable int userId){

        userService.deleteUserById(userId);

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.UPDATED)
                .message("user deleted successfully")
                .build();


        return ResponseEntity.ok(response);
    }

}
