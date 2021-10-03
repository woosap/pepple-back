package woosap.Pepple.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import woosap.Pepple.entity.Room;
import woosap.Pepple.entity.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    @Transactional
    void deleteByRoom(Room room);
}
