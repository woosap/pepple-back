package woosap.Pepple.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woosap.Pepple.dto.ResponseDTO;
import woosap.Pepple.dto.SessionSaveInfo;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.entity.User;
import woosap.Pepple.service.UserServiceImpl;
import woosap.Pepple.util.resolver.SavedInfo;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/user")
    public ResponseEntity<ResponseDTO> joinWithDetails(@SavedInfo SessionSaveInfo savedInfo, @RequestBody UserDTO userDTO) {

        if (savedInfo == null) {
            ResponseDTO responseDTO = new ResponseDTO("잘못된 접근입니다", false);
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
        userDTO.setUserId(savedInfo.getUserId());
        userDTO.setImageUrl(savedInfo.getImageUrl());
        userDTO.setNickname(savedInfo.getNickname());

        User savedUser = userService.join(userDTO.toEntity());

        if (savedUser == null) {
            ResponseDTO responseDTO = new ResponseDTO("데이터베이스 에러", false);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ResponseDTO responseDTO = new ResponseDTO("회원가입에 성공하였습니다", true);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

}
