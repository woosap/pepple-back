package woosap.Pepple.service;

import java.util.List;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.entity.User;

public interface UserService {

    public User join(User user, UserDTO userDTO);

    public Boolean nicknameDuplicateCheck(String nickname);

    public User findUser(String id);

    public void updateUser(UserDTO userDTO);

    public List<UserDTO> getUserDetails(long roomId);
}
