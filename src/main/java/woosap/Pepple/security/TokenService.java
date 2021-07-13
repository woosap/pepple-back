package woosap.Pepple.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    // 토큰 생성
    String createToken(String subject);

    // 토큰에 저장된 정보를 가져옴
    String getSubject(String token);

    // 요청의 헤더에서 토큰의 정보를 가져옴
    String resolveToken(HttpServletRequest request);

    // 토큰에서 인증정보 조회
    Authentication getAuthentication(String token);

    // 토큰의 유효성 및 만료일자 확인
    boolean validToken(String jwtToken);
}
