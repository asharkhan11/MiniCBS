package in.banking.cbs.action_service.controller;

import in.banking.cbs.action_service.DTO.UserDto;
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

        User user = userService.createUser(mapper.mapDtoToUser(userDto));

        Response<User> response = Response.<User>builder()
                .status(ResponseStatus.CREATED)
                .message("user created successfully")
                .data(user)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Response<User>> updateUser(@PathVariable int userId, @RequestBody UserDto userDto){
        return null;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Response<Void>> deleteUser(@PathVariable int userId){
        return null;
    }

}
