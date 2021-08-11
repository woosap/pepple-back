package woosap.Pepple.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import woosap.Pepple.entity.Room;

public interface RoomRepository extends JpaRepository<Room, String> {

    public Boolean existsByTitle(String title);

    Optional<Room> findByRoomId(long roomId);

    @Query("SELECT r FROM Room r JOIN FETCH r.category ORDER BY r.date DESC")
    List<Room> findAllWithRoomType(Pageable pageable);
}
