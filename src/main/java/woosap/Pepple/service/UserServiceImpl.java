package woosap.Pepple.service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.entity.User;
import woosap.Pepple.entity.UserRoom;
import woosap.Pepple.entity.UserSNS;
import woosap.Pepple.repository.RoomRepository;
import woosap.Pepple.repository.UserRepository;
import woosap.Pepple.repository.UserRoomRepository;
import woosap.Pepple.repository.UserSNSRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserSNSRepository userSNSRepository;
    private final UserRoomRepository userRoomRepository;
    private final RoomRepository roomRepository;


    @Override
    public User join(User user, UserDTO userDTO) {
        user.setUserId(userDTO.getUserId());
        user.setProfile(userDTO.getProfile());
        user.setImageUrl(userDTO.getImageUrl());
        user.setJob(userDTO.getJob());
        user.setNickname(userDTO.getNickname());
        User savedUser = userRepository.save(user);
        List<String> snsList = userDTO.getSnsList();
        snsList
            .forEach
            (sns -> {
                UserSNS userSNS = new UserSNS();
                  userSNS.setUser(savedUser);
                  userSNS.setSns(sns);
                  userSNSRepository.save(userSNS);
            });
        return savedUser;
    }

    @Override
    public Boolean nicknameDuplicateCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public User findUser(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User foundUser = userRepository.findById(userDTO.getUserId())
            .orElseThrow(RuntimeException::new);
        foundUser.setImageUrl(userDTO.getImageUrl());
        foundUser.setJob(userDTO.getJob());
        foundUser.setNickname(userDTO.getNickname());
        foundUser.setProfile(userDTO.getProfile());
        User savedUser = userRepository.save(foundUser);
        userSNSRepository.deleteAllByUser(foundUser);
        List<String> snsList = userDTO.getSnsList();
        snsList
            .forEach
                (sns -> {
                    UserSNS userSNS = new UserSNS();
                    userSNS.setUser(savedUser);
                    userSNS.setSns(sns);
                    userSNSRepository.save(userSNS);
                });
    }

    @Override
    public List<UserDTO> getUserDetails(long roomId) {
        Set<UserRoom> userRoomIds = roomRepository.findByRoomId(roomId)
                                    .get().getUserRoom();

        List<UserRoom> sortedByOrder = userRoomIds.stream()
            .sorted(Comparator.comparing(UserRoom::getUserRoomId))
            .collect(Collectors.toList());

        return sortedByOrder
            .stream()
            .map(UserRoom::getUserId)
            .map(userId -> userRepository.findById(userId).get())
            .map(user -> UserDTO.builder()
                .userId(user.getUserId())
                .imageUrl(user.getImageUrl())
                .job(user.getJob())
                .nickname(user.getNickname())
                .profile(user.getProfile())
                .snsList(user.getSnsList().stream().map(UserSNS::getSns)
                    .collect(Collectors.toList()))
                .build())
            .collect(Collectors.toList());
    }
}
