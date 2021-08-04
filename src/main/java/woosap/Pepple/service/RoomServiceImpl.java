package woosap.Pepple.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
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
        room.setSubTitle(roomDTO.getSubTitle());
        room.setDate(roomDTO.getDate());
        room.setCreator(roomDTO.getCreator());
        room.setCategory(roomDTO.getCategory());
        room.setCapacity(roomDTO.getCapacity());

        return roomRepository.save(room);
    }

    @Override
    public void plusUserCount(Room room) {
        Room tmp = new Room();
        tmp.setPeoples(room.getPeoples() + 1);
        roomRepository.save(tmp);
    }

    @Override
    public void minusUserCount(Room room) {
        Room tmp = new Room();
        tmp.setPeoples(room.getPeoples() - 1);
        roomRepository.save(tmp);
    }

    @Override
    public void removeRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setTitle(roomDTO.getTitle());
        room.setSub_title(roomDTO.getSub_title());
        room.setDate(roomDTO.getDate());
        room.setMaker(roomDTO.getMaker());
        room.setCategory(roomDTO.getCategory());
        room.setCapacity(roomDTO.getCapacity());
        room.setPeoples(roomDTO.getPeoples());
        room.setUserV(roomDTO.getUserV());
        roomRepository.delete(room);
    }

    @Override
    public Page<Room> getRoomsWithPage(Pageable page) {
        return roomRepository.findAll(page);
    }
}
