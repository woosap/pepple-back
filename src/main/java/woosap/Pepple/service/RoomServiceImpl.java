package woosap.Pepple.service;

import org.springframework.stereotype.Service;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.entity.Room;
import woosap.Pepple.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Boolean titleDuplicateCheck(String title) {
        return roomRepository.existsByTitle(title);
    }

    @Override
    public Boolean checkCapacity(int capacity, int peoples) {
        if (capacity >= peoples) {
            return true;
        }
        return false;
    }

    @Override
    public Room createRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setTitle(roomDTO.getTitle());
        room.setSub_title(roomDTO.getSub_title());
        room.setDate(roomDTO.getDate());
        room.setMaker(roomDTO.getMaker());
        room.setCategory(roomDTO.getCategory());
        room.setCapacity(roomDTO.getCapacity());
        room.setPeoples(1);
        room.setUserV(roomDTO.getUserV());

        return room;
    }
}
