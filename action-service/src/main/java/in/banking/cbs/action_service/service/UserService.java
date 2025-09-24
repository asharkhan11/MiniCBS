package in.banking.cbs.action_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.banking.cbs.action_service.DTO.UserDtoUpdateBasic;
import in.banking.cbs.action_service.entity.User;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserBasicDetailsById(int userId, UserDtoUpdateBasic userDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found with id : " + userId));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setDob(userDto.getDob());
        user.setPhone(userDto.getPhone());
        user.setAddress(userDto.getAddress());

        User save = userRepository.save(user);

        return save;
    }

    public void deleteUserById(int userId) {

        userRepository.deleteById(userId);

    }
}
