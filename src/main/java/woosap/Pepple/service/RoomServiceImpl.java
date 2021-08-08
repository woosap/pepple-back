package woosap.Pepple.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.dto.UserRoomDTO;
import woosap.Pepple.entity.Room;
import woosap.Pepple.entity.UserRoom;
import woosap.Pepple.repository.RoomRepository;
import woosap.Pepple.repository.UserRoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final UserRoomRepository userRoomRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
        UserRoomRepository userRoomRepository) {
        this.roomRepository = roomRepository;
        this.userRoomRepository = userRoomRepository;
    }

    @Override
    public Boolean titleDuplicateCheck(String title) {
        return roomRepository.existsByTitle(title);
    }

    @Override
    public Boolean checkCapacity(UserRoomDTO userRoomDTO) {
        int peopleCount = userRoomRepository.countByRoomId(userRoomDTO.getRoomId());
        Room room = roomRepository.findByRoomId(userRoomDTO.getRoomId())
            .orElseThrow(RuntimeException::new);
        if (room.getCapacity() > peopleCount) {
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
    public UserRoom enterRoom(UserRoomDTO userRoomDTO) {
        UserRoom userRoom = new UserRoom();
        userRoom.setUserId(userRoomDTO.getUserId());
        userRoom.setRoomId(userRoomDTO.getRoomId());

        return userRoomRepository.save(userRoom);
    }

    @Override
    public void removeRoom(UserRoomDTO userRoomDTO) {
        Room room = roomRepository.findByRoomId(userRoomDTO.getRoomId())
            .orElseThrow(RuntimeException::new);
        roomRepository.delete(room);
    }

    @Override
    public Page<Room> getRoomsWithPage(Pageable page) {
        return roomRepository.findAll(page);
    }

    @Override
    public Boolean checkPeopleCount(UserRoomDTO userRoomDTO) {
        int peopleCount = userRoomRepository.countByRoomId(userRoomDTO.getRoomId());
        if (peopleCount != 0) {
            return false;
        }
        return true;
    }
}
