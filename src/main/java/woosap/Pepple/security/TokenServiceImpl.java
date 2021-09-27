package woosap.Pepple.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Slf4j
@Component
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret_key}")
    private String secret_key;

    private final UserDetailsService userDetailsService;

    @Override
    public String createToken(String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secret_key);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        Date now = new Date();
        long access_TokenValidTime = 30 * 24 * 60 * 60 * 1000L;
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(now)
            .signWith(signingKey, signatureAlgorithm)
            .setExpiration(new Date(now.getTime() + access_TokenValidTime))
            .compact();
    }

    // 비밀키로 토큰을 풀어 값을 가져오는 역할
   @Override
    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secret_key))
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }

    @Override
    public boolean validToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secret_key)
                .build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("유효하지 않은 토큰");
            return false;
        }
    }
}