package woosap.Pepple.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woosap.Pepple.dto.ResponseDTO;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.dto.RoomDetailsDTO;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.dto.UserRoomDTO;
import woosap.Pepple.entity.Room;
import woosap.Pepple.service.RoomServiceImpl;
import woosap.Pepple.service.UserService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomServiceImpl roomService;
    private final UserService userService;

    @PostMapping("/enter")
    public ResponseEntity<?> enterRoom(@Valid @RequestBody UserRoomDTO userRoomInfo) {
        log.info("enterRoom call");
        if (!roomService.checkCapacity(userRoomInfo)) {
            return new ResponseEntity<>(new ResponseDTO("입장 인원을 초과하였습니다", false),
                HttpStatus.CONFLICT);
        }
        roomService.enterRoom(userRoomInfo);
        return new ResponseEntity<>(new ResponseDTO("방에 입장했습니다.", true), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> creatRoom(@Valid @RequestBody RoomDTO roomInfo) {
        log.info("creatRoom call");
        if (roomService.titleDuplicateCheck(roomInfo.getTitle())) {
            return new ResponseEntity<>(new ResponseDTO("사용 중인 방 제목입니다", false),
                HttpStatus.CONFLICT);
        }
        roomService.createRoom(roomInfo);
        return new ResponseEntity<>(new ResponseDTO("방을 만들었습니다", true), HttpStatus.CREATED);
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveRoom(@Valid @RequestBody UserRoomDTO userRoomInfo) {
        log.info("{} leaves the room: {}", userRoomInfo.getUserId(), userRoomInfo.getRoomId());
        roomService.leaveRoom(userRoomInfo);
        return new ResponseEntity<>(new ResponseDTO("방에서 나갔습니다", true), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getRooms(Pageable page) {
        log.info("getRooms called");
        log.info("page is {}", page);
        List<Room> roomsWithPage = roomService.getRoomsWithPage(page);
        List<RoomDTO> rooms = roomsWithPage
            .stream()
            .map(roomEntity -> roomEntity.entityToDto(roomEntity))
            .collect(Collectors.toList());
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomDetails(@PathVariable long roomId) {
        log.info("getRoomDetails called id is : {}", roomId);
        RoomDTO roomDetails = roomService.getRoomDetails(roomId);
        List<UserDTO> userDetails = userService.getUserDetails(roomId);
        RoomDetailsDTO result = new RoomDetailsDTO(roomDetails, userDetails);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
