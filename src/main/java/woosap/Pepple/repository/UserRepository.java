package woosap.Pepple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woosap.Pepple.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
