package woosap.Pepple.service;

import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import woosap.Pepple.dto.UserDTO;
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

    @Override
    public User findUser(String id) {
        return userRepository.findById(id)
            .orElseThrow( () -> new RuntimeException());
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User foundUser = userRepository.findById(userDTO.getUserId()).orElseThrow(RuntimeException::new);
        foundUser.setImageUrl(userDTO.getImageUrl());
        foundUser.setJob(userDTO.getJob());
        foundUser.setNickname(userDTO.getNickname());
        foundUser.setProfile(userDTO.getProfile());
        foundUser.setSnsList(userDTO.getSnsList());
        userRepository.save(foundUser);
    }
}
