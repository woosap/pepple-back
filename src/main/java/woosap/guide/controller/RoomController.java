package woosap.guide.controller;

import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import woosap.guide.dto.RoomDTO;

@Controller
@RequestMapping("/api/room")
public class RoomController {

    public static Long roomCount = 0L;
    public static List<RoomDTO> roomList = new ArrayList<>();

    // TODO:  Oauth
    @GetMapping
    @ApiOperation(value = "방 목록 불러오기", notes = "초기에 방 리스트를 불러옵니다.")
    public ResponseEntity<List<RoomDTO>> getRoomInfo(){

        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "방 생성")
    public ResponseEntity<String> createRoom(RoomDTO roomInfo) {
        roomList.add(roomInfo);
        ++roomCount;
        return new ResponseEntity<>("created", HttpStatus.CREATED);
    }
}
