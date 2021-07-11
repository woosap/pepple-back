package woosap.Pepple.service;

import woosap.Pepple.entity.User;

public interface UserService {
    public User join(User user);
    public Boolean nicknameDuplicateCheck(String nickname);
}
