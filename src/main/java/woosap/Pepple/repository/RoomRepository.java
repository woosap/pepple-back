package woosap.Pepple.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import woosap.Pepple.entity.Room;

public interface RoomRepository extends JpaRepository<Room, String> {

    public Boolean existsByTitle(String title);
}
