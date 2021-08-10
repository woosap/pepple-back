package woosap.Pepple.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import woosap.Pepple.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByNickname(String nickname);

    @Query("SELECT u FROM User u join fetch u.snsList")
    Optional<User> findById(String userId);
}
