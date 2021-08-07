package woosap.Pepple.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.entity.Room;
import woosap.Pepple.entity.UserRoom;

public interface RoomService {

    public Boolean titleDuplicateCheck(String title);

    public Boolean checkCapacity(int capacity, int peoples);

    public Room createRoom(RoomDTO roomDTO);

    public UserRoom enterRoom(UserDTO userDTO, RoomDTO roomDTO);

    public Page<Room> getRoomsWithPage(Pageable page);

    public void removeRoom(Room room);

}