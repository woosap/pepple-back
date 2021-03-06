package woosap.Pepple.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import woosap.Pepple.entity.Room;

public interface RoomRepository extends JpaRepository<Room, String> {

    Boolean existsByTitle(String title);

    Optional<Room> findByRoomId(long roomId);

    @Query("SELECT DISTINCT r FROM Room r INNER JOIN r.userRoom "
        + "INNER JOIN r.category c "
        + "ORDER BY r.date DESC")
    List<Room> findAllWithRoomType(Pageable pageable);
}
