package woosap.guide.controller;

import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woosap.guide.config.JwtUtil;
import woosap.guide.dto.AuthRequestDTO;
import woosap.guide.dto.AuthResponseDTO;
import woosap.guide.dto.UserDTO;

@RestController
@RequestMapping("/api")
public class UserController {

    final UserDetailsService detailsService;
    final PasswordEncoder passwordEncoder;
    final JwtUtil jwtUtil;
    final AuthenticationManager authenticationManager;

    //in-memory for test
    public static Map<String, String> repository = new HashMap<>();

    public UserController(
        @Qualifier("customUserDetailService") UserDetailsService detailsService,
        PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
        AuthenticationManager authenticationManager) {
        this.detailsService = detailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    @ApiOperation(value = "인증, 및 로그인", notes = "성공시 토큰이 발행됩니다")
    public ResponseEntity<AuthResponseDTO> auth(AuthRequestDTO authRequestDTO) {

        String rowPassword = authRequestDTO.getPassword();
        String password = repository.get(authRequestDTO.getEmail());

        if ((password != null) && passwordEncoder.matches(rowPassword, password)) {

            String token = jwtUtil.generateToken(detailsService.loadUserByUsername(authRequestDTO.getEmail()));

            AuthResponseDTO response = new AuthResponseDTO(token);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<String> join(UserDTO userDTO) {
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        repository.put(userDTO.getEmail(), encodedPassword);
        return new ResponseEntity<>("created", HttpStatus.CREATED);
    }



}