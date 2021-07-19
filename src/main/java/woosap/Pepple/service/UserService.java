package woosap.Pepple.service;

import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.entity.User;

public interface UserService {

    public User join(User user);

    public Boolean nicknameDuplicateCheck(String nickname);

    public User findUser(String id);

    public void updateUser(UserDTO userDTO);
}
