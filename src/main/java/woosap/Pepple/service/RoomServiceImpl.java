package woosap.Pepple.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.dto.UserRoomDTO;
import woosap.Pepple.entity.Room;
import woosap.Pepple.entity.RoomType;
import woosap.Pepple.entity.UserRoom;
import woosap.Pepple.entity.type.Category;
import woosap.Pepple.repository.RoomRepository;
import woosap.Pepple.repository.RoomTypeRepository;
import woosap.Pepple.repository.UserRoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final UserRoomRepository userRoomRepository;

    private final RoomTypeRepository roomTypeRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
        UserRoomRepository userRoomRepository,
        RoomTypeRepository roomTypeRepository) {
        this.roomRepository = roomRepository;
        this.userRoomRepository = userRoomRepository;
        this.roomTypeRepository = roomTypeRepository;
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
    public void createRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setTitle(roomDTO.getTitle());
        room.setSubTitle(roomDTO.getSubTitle());
        room.setDate(LocalDateTime.now());
        room.setCapacity(roomDTO.getCapacity());
        Room savedRoom = roomRepository.save(room);
        List<Category> categoryList = roomDTO.getCategory();
        List<RoomType> roomTypeList = categoryList
            .stream()
            .map(category -> new RoomType(null, savedRoom, category))
            .collect(Collectors.toList());
        roomTypeRepository.saveAll(roomTypeList);

    }

    @Override
    public UserRoom enterRoom(UserRoomDTO userRoomDTO) {
        UserRoom userRoom = new UserRoom();
        userRoom.setUserId(userRoomDTO.getUserId());
        userRoom.setRoomId(userRoomDTO.getRoomId());

        return userRoomRepository.save(userRoom);
    }

    @Override
    public void removeRoom(long roomId) {
        Room room = roomRepository.findByRoomId(roomId)
            .orElseThrow(RuntimeException::new);
        roomRepository.delete(room);
    }

    @Override
    @Transactional
    public void leaveRoom(UserRoomDTO userRoomDTO) {
        long roomId = userRoomDTO.getRoomId();
        String userId = userRoomDTO.getUserId();
        userRoomRepository.leaveUserFromRoom(roomId, userId);
        // 방에 사람이 없을 경우 -> 다 나간 케이스 -> 삭제
        if (!userRoomRepository.existsByRoomId(roomId)) {
            removeRoom(roomId);
        }
    }

    @Override
    public List<Room> getRoomsWithPage(Pageable page) {
        return roomRepository.findAllWithRoomType(page);
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
