package woosap.Pepple.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woosap.Pepple.dto.ResponseDTO;
import woosap.Pepple.dto.SessionSaveInfo;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.entity.User;
import woosap.Pepple.repository.UserRepository;
import woosap.Pepple.security.SecurityServiceImpl;
import woosap.Pepple.service.UserServiceImpl;
import woosap.Pepple.util.resolver.SavedInfo;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final SecurityServiceImpl securityServiceImpl;

    @PostMapping("/user")
    public ResponseEntity<ResponseDTO> joinWithDetails(@SavedInfo SessionSaveInfo savedInfo, @RequestBody UserDTO userDTO,
                                                        HttpServletRequest request) {

        if (savedInfo == null) {
            ResponseDTO responseDTO = new ResponseDTO("잘못된 접근입니다", false);
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
        userDTO.setUserId(savedInfo.getUserId());

        User savedUser = userService.join(userDTO.toEntity());

        if (savedUser == null) {
            ResponseDTO responseDTO = new ResponseDTO("데이터베이스 에러", false);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        request.getSession().invalidate();
        ResponseDTO responseDTO = new ResponseDTO("회원가입에 성공하였습니다", true);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/nickname")
    public ResponseEntity<String> checkNickname(@RequestParam(name = "nickname") String nickname) {
        Boolean result = userService.nicknameDuplicateCheck(nickname);

        if (result) {
            return new ResponseEntity<>("이미 사용중인 별명입니다", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("사용 가능한 닉네임 입니다", HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findById(user.get("user_id")).orElseThrow(() -> new IllegalArgumentException("가입하지 않았습니다"));
        return securityServiceImpl.createToken(member.getUserId());
    }
}
