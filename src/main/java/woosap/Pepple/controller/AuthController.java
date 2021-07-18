package woosap.Pepple.controller;

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
    public AuthResponseDTO checkAuthenticated() {
        return new AuthResponseDTO(true);
    }

    @GetMapping("/detail")
    public ResponseEntity<UserDTO> getCurrentUserDetail(@AuthenticationPrincipal CustomUserDetails currentUser) {
        UserDTO detail = new UserDTO(
            currentUser.getId(),
            currentUser.getNickname(),
            currentUser.getImageUrl(),
            currentUser.getJob(),
            currentUser.getProfile(),
            currentUser.getSnsList());
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }
}
