package woosap.Pepple.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import woosap.Pepple.entity.User;
import woosap.Pepple.entity.UserRoom;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {

    int countByRoomId(long roomId);
    boolean existsByRoomId(long roomId);

    @Modifying
    @Query("DELETE FROM UserRoom u WHERE u.roomId = ?1 AND u.userId = ?2")
    void leaveUserFromRoom(long roomId, String userId);
}
