package woosap.Pepple.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import woosap.Pepple.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByNickname(String nickname);

    Optional<User> findById(String userId);
}
