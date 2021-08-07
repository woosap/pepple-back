package woosap.Pepple.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.dto.UserDTO;
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
    public Boolean checkCapacity(RoomDTO roomDTO) {
        long peoples = userRoomRepository.countByUserId(roomDTO.getRoomId());
        if (roomDTO.getCapacity() > peoples) {
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
    public UserRoom enterRoom(UserDTO userDTO, RoomDTO roomDTO) {
        UserRoom userRoom = new UserRoom();
        userRoom.setUserId(userDTO.getUserId());
        userRoom.setRoomId(roomDTO.getRoomId());

        return userRoomRepository.save(userRoom);
    }

    @Override
    public void removeRoom(Room room) {

        roomRepository.delete(room);
    }

    @Override
    public Page<Room> getRoomsWithPage(Pageable page) {
        return roomRepository.findAll(page);
    }
}
