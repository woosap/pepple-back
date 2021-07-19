package woosap.Pepple.controller;

import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woosap.Pepple.dto.ResponseDTO;
import woosap.Pepple.dto.SessionSaveInfo;
import woosap.Pepple.dto.TokenDTO;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.entity.User;
import woosap.Pepple.security.TokenServiceImpl;
import woosap.Pepple.service.UserServiceImpl;
import woosap.Pepple.util.resolver.SavedInfo;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;
    private final TokenServiceImpl tokenService;

    @PostMapping("/user")
    @ApiOperation(value = "회원가입시 추가정보를 입력", notes = "성공시 토큰을 리턴합니다")
    public ResponseEntity<?> joinWithDetails(@SavedInfo SessionSaveInfo savedInfo, @Valid UserDTO userDTO,
                                                        BindingResult bindingResult,
                                                        HttpServletRequest request) {

        log.info("join with Details called savedInfo is {}", savedInfo);

        if (savedInfo == null || !userDTO.getUserId().equals(savedInfo.getUserId())) {
            ResponseDTO responseDTO = new ResponseDTO("잘못된 접근입니다", false);
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                .forEach(error -> log.error(error.getDefaultMessage()));
        }

        User user = new User();

        BeanUtils.copyProperties(userDTO, user);

        User savedUser = userService.join(user);

        if (savedUser == null) {
            ResponseDTO responseDTO = new ResponseDTO("데이터베이스 에러", false);
            log.error("Database Error -> failed to save");
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String token = tokenService.createToken(savedInfo.getUserId());
        request.getSession().invalidate();
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.CREATED);
    }

    @PutMapping("/user")
    @ApiOperation(value = "유저정보를 수정합니다", notes = "")
    public ResponseEntity<ResponseDTO> updateUserInfo(@RequestBody @Valid UserDTO userInfo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                .forEach(error -> log.error(error.getDefaultMessage()));
            return new ResponseEntity<>(new ResponseDTO("입력오류, 입력값들을 다시 확인해주세요", false), HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(userInfo);
        return new ResponseEntity<>(new ResponseDTO("수정되었습니다", true), HttpStatus.OK);
    }

    @GetMapping("/nickname")
    @ApiOperation(value = "닉네임", notes = "닉네임 중복체크")
    public ResponseEntity<String> checkNickname(@RequestParam(name = "nickname") String nickname) {
        Boolean result = userService.nicknameDuplicateCheck(nickname);

        if (result) {
            return new ResponseEntity<>("이미 사용중인 별명입니다", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("사용 가능한 닉네임 입니다", HttpStatus.OK);
    }
}
