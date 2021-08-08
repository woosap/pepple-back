package woosap.Pepple.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import woosap.Pepple.entity.User;
import woosap.Pepple.entity.UserRoom;

public interface UserRoomRepository extends JpaRepository<UserRoom, String> {

    long countByRoomId(long roomId);
}
