package woosap.Pepple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import woosap.Pepple.entity.Room;
import woosap.Pepple.entity.UserRoom;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {

    int countByRoom(Room room);
    boolean existsByRoom(Room room);

    @Modifying
    @Query("DELETE FROM UserRoom u WHERE u.userId = ?1")
    void leaveUserFromRoom(String userId);
}
