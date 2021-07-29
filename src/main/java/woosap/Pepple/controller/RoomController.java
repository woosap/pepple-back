package woosap.Pepple.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import woosap.Pepple.dto.ResponseDTO;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.entity.Room;
import woosap.Pepple.security.TokenServiceImpl;
import woosap.Pepple.service.RoomServiceImpl;
import woosap.Pepple.service.UserServiceImpl;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final UserServiceImpl userService;
    private final TokenServiceImpl tokenService;
    private final RoomServiceImpl roomService;

    @GetMapping("/title")
    public ResponseEntity<String> checkTitle(@RequestParam(name = "title") String title) {
        Boolean results = roomService.titleDuplicateCheck(title);
        if (results) {
            return new ResponseEntity<>("사용 중인 방 제목입니다", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("사용 가능한 방 제목입니다", HttpStatus.OK);
    }

    @GetMapping("/capacity")
    public ResponseEntity<String> checkCapacity(@RequestParam(name = "peoples") int peoples,
        @Valid RoomDTO roomDTO) {

        if (!roomService.checkCapacity(roomDTO.getCapacity(), peoples)) {
            return new ResponseEntity<>("입장 인원을 초과하였습니다.", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("입장할수 있습니다", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> creatRoom(@Valid RoomDTO roomInfo,
        HttpServletRequest httpServletRequest) {

        roomService.createRoom(roomInfo);
        return new ResponseEntity<>(new ResponseDTO("방을 만들었습니다", true), HttpStatus.CREATED);
    }

    @GetMapping
    public List<RoomDTO> getRooms(Pageable page) {
        log.info("getRooms called");
        log.info("page is {}", page);
        Page<Room> roomsWithPage = roomService.getRoomsWithPage(page);
    }
}
