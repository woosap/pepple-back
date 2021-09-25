package woosap.Pepple.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.dto.RoomWithUserImageDTO;
import woosap.Pepple.dto.UserRoomDTO;
import woosap.Pepple.entity.Room;
import woosap.Pepple.entity.RoomType;
import woosap.Pepple.entity.User;
import woosap.Pepple.entity.UserRoom;
import woosap.Pepple.entity.type.Category;
import woosap.Pepple.repository.RoomRepository;
import woosap.Pepple.repository.RoomTypeRepository;
import woosap.Pepple.repository.UserRepository;
import woosap.Pepple.repository.UserRoomRepository;

@Service
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    private final UserRoomRepository userRoomRepository;

    private final RoomTypeRepository roomTypeRepository;

    private final UserRepository userRepository;

    public RoomService(RoomRepository roomRepository,
        UserRoomRepository userRoomRepository,
        RoomTypeRepository roomTypeRepository,
        UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRoomRepository = userRoomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.userRepository = userRepository;
    }

    public Boolean titleDuplicateCheck(String title) {
        return roomRepository.existsByTitle(title);
    }

    public Boolean checkCapacity(UserRoomDTO userRoomDTO) {
        int peopleCount = userRoomRepository.countByRoomId(userRoomDTO.getRoomId());
        Room room = roomRepository.findByRoomId(userRoomDTO.getRoomId())
            .orElseThrow(RuntimeException::new);
        if (room.getCapacity() > peopleCount) {
            return true;
        }
        return false;
    }

    public long createRoom(RoomDTO roomDTO) {
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

        return savedRoom.getRoomId();
    }

    public UserRoom enterRoom(UserRoomDTO userRoomDTO) {
        UserRoom userRoom = new UserRoom();
        User user = userRepository.findById(userRoomDTO.getUserId()).get();
        userRoom.setUserId(userRoomDTO.getUserId());
        userRoom.setRoomId(userRoomDTO.getRoomId());
        userRoom.setImageUrl(user.getImageUrl());
        return userRoomRepository.save(userRoom);
    }

    private void removeRoom(long roomId) {
        Room room = roomRepository.findByRoomId(roomId)
            .orElseThrow(RuntimeException::new);
        roomTypeRepository.deleteByRoomId(roomId);
        //room.setCategory(null);
        roomRepository.delete(room);
    }

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

    public List<RoomWithUserImageDTO> getRoomsWithUserImages(Pageable page) {

        List<RoomWithUserImageDTO> result = new ArrayList<>();

        List<Room> roomlist = roomRepository.findAllWithRoomType(page);

        roomlist.forEach(room -> {
            RoomWithUserImageDTO toAdd = this.convert(room);
            List<UserRoom> userList = userRoomRepository.findAllByRoomId(room.getRoomId());
            List<String> userImageUrls = userList.stream().map(user -> user.getImageUrl())
                .collect(Collectors.toList());
            toAdd.setImageUrl(userImageUrls);
            result.add(toAdd);
        });
        return result;
    }


    public RoomDTO getRoomDetails(long roomId) {
        Optional<Room> roomInfo = roomRepository.findByRoomId(roomId);
        if (roomInfo.isEmpty()) {
            log.error("Wrong roomId : {}", roomId);
            throw new RuntimeException("잘못된 roomId: " + roomId);
        }
        return Room.entityToDto(roomInfo.get());
    }

    private RoomWithUserImageDTO convert(Room room) {
        RoomWithUserImageDTO result = new RoomWithUserImageDTO();
        result.setRoomId(room.getRoomId());
        result.setCategory(room.getCategory().stream().map(s -> s.getCategory()).collect(Collectors.toList()));
        result.setCapacity(room.getCapacity());
        result.setDate(room.getDate());
        result.setSubTitle(room.getSubTitle());
        result.setTitle(room.getTitle());
        return result;
    }
}
