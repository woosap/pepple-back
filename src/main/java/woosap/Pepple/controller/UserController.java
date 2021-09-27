package woosap.Pepple.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woosap.Pepple.dto.ResponseDTO;
import woosap.Pepple.dto.TokenDTO;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.dto.agora.AgoraTokenDTO;
import woosap.Pepple.entity.User;
import woosap.Pepple.security.TokenServiceImpl;
import woosap.Pepple.service.AgoraService;
import woosap.Pepple.service.UserServiceImpl;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;
    private final TokenServiceImpl tokenService;
    private final AgoraService agoraService;

    @PostMapping("/user")
    @ApiOperation(value = "회원가입시 추가정보를 입력", notes = "성공시 토큰을 리턴합니다")
    public ResponseEntity<?> joinWithDetails(@Valid @RequestBody UserDTO userDTO) {
        log.info("join with Details called");
        User user = new User();
        userService.join(user, userDTO);
        String token = tokenService.createToken(userDTO.getUserId());
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.CREATED);
    }

    @PutMapping("/user")
    @ApiOperation(value = "유저정보를 수정합니다", notes = "")
    public ResponseEntity<ResponseDTO> updateUserInfo(@RequestBody @Valid UserDTO userInfo) {
        userService.updateUser(userInfo);
        return new ResponseEntity<>(new ResponseDTO("수정되었습니다", true), HttpStatus.OK);
    }

    @GetMapping("/nickname")
    @ApiOperation(value = "닉네임", notes = "닉네임 중복체크")
    public ResponseEntity<String> checkNickname(@RequestParam(name = "nickname") String nickname) {
        if (nickname.length() > 8) {
            return new ResponseEntity<>("닉네임은 8글자 까지입니다.", HttpStatus.BAD_REQUEST);
        }

        Boolean result = userService.nicknameDuplicateCheck(nickname);

        if (result) {
            return new ResponseEntity<>("이미 사용중인 별명입니다", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("사용 가능한 닉네임 입니다", HttpStatus.OK);
    }

    @PostMapping("/agoraToken")
    public ResponseEntity<TokenDTO> getAgoraAuthToken(@RequestBody AgoraTokenDTO agoraTokenDTO) {
        log.info("/agoraToken called");
        TokenDTO result = agoraService.getAgoraAuthToken(agoraTokenDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
