package woosap.Pepple.service;

import org.springframework.stereotype.Service;
import woosap.Pepple.entity.User;
import woosap.Pepple.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User join(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean nicknameDuplicateCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
