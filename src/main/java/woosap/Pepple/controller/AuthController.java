package woosap.Pepple.controller;

import io.swagger.annotations.ApiOperation;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woosap.Pepple.dto.AuthResponseDTO;
import woosap.Pepple.dto.UserDTO;
import woosap.Pepple.dto.oauth2.CustomUserDetails;
import woosap.Pepple.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "토큰을 통해서 해당 사용자가 인증되었는지 체크를 합니다", notes = "성공시 인증여부를 리턴합니다.")
    public AuthResponseDTO checkAuthenticated() {
        return new AuthResponseDTO(true);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "로그인 된 유저 세부사항", notes = "나오는 파라미터는 무시하셔도 되고, 토큰과 함께 파라미터 없이 요청하시면 해당 사용자의 세부정보를 리턴합니다.")
    public ResponseEntity<UserDTO> getCurrentUserDetail(@AuthenticationPrincipal CustomUserDetails currentUser) {
        UserDTO detail = new UserDTO(
            currentUser.getId(),
            currentUser.getNickname(),
            currentUser.getImageUrl(),
            currentUser.getJob(),
            currentUser.getProfile(),
            currentUser.getSnsList().stream().map(user -> user.getSns()).collect(Collectors.toList()));
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }
}
