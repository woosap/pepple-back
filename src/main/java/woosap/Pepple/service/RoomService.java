package woosap.Pepple.service;

import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.entity.Room;

public interface RoomService {

    public Boolean titleDuplicateCheck(String title);

    public Room createRoom(RoomDTO roomDTO);

}