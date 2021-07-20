package woosap.Pepple.service;

import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.entity.Room;

public interface RoomService {

    public Boolean titleDuplicateCheck(String title);

    public Boolean checkCapacity(int capacity, int peoples);

    public Room createRoom(RoomDTO roomDTO);

    public void plusUserCount(Room room);

    public void minusUserCount(Room room);

    public void removeRoom(RoomDTO roomDTO);

}