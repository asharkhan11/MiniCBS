package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.entity.User;
import in.banking.cbs.action_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

}
