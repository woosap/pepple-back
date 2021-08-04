package woosap.Pepple.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.entity.Room;

public interface RoomService {

    public Boolean titleDuplicateCheck(String title);

    public Boolean checkCapacity(int capacity, int peoples);

    public Room createRoom(RoomDTO roomDTO);

    public Page<Room> getRoomsWithPage(Pageable page);
    public void plusUserCount(Room room);

    public void minusUserCount(Room room);

    public void removeRoom(RoomDTO roomDTO);

}