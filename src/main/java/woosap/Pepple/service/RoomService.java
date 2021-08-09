package woosap.Pepple.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.dto.UserRoomDTO;
import woosap.Pepple.entity.Room;
import woosap.Pepple.entity.UserRoom;

public interface RoomService {

    public Boolean titleDuplicateCheck(String title);

    public Boolean checkCapacity(UserRoomDTO userRoomDTO);

    public void createRoom(RoomDTO roomDTO);

    public UserRoom enterRoom(UserRoomDTO userRoomDTO);

    public List<Room> getRoomsWithPage(Pageable page);

    public Boolean checkPeopleCount(UserRoomDTO userRoomDTO);

    public void removeRoom(long roomId);

    public void leaveRoom(UserRoomDTO userRoomDTO);

}