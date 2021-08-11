package woosap.Pepple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woosap.Pepple.entity.User;
import woosap.Pepple.entity.UserSNS;

public interface UserSNSRepository extends JpaRepository<UserSNS, Long> {

    void deleteAllByUser(User user);
}
