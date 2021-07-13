package woosap.Pepple.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Component
public class SecurityServiceImpl implements SecurityService {

    @Value("${jwt.secret_key}")
    private String secret_key;

    @Value("$(jwt.access_tokenValidTime}")
    private long access_TokenValidTime;

    @Value("${jwt.refresh_TokenValidTime}")
    private long refresh_TokenValidTime;

    private final UserDetailsService userDetailsService;

    // 서버에서 토큰을 발행하는 역할
    @Override
    public String createToken(String subject) {
        // 토큰을 서명하기 위해 사용할 알고리즘 선택
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secret_key);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        Date now = new Date();
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(now)
            .signWith(signingKey, signatureAlgorithm)
            .setExpiration(new Date(now.getTime() + access_TokenValidTime))
            .compact();
    }

    public String refreshToken(String value) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secret_key);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        Date now = new Date();
        return Jwts.builder()
            .setSubject(value)
            .setIssuedAt(now)
            .signWith(signingKey, signatureAlgorithm)
            .setExpiration(new Date(now.getTime() + refresh_TokenValidTime))
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
        return request.getHeader("AUTH-TOKEN");
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }

    @Override
    public boolean validToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret_key).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


}