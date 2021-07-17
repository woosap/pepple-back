package woosap.Pepple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woosap.Pepple.entity.Room;

public interface RoomRepository extends JpaRepository<Room, String> {

    Boolean existsByTitle(String title);
}
