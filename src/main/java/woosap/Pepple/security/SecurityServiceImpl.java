package woosap.Pepple.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    public static final String SECRET_KEY = "aasjjkjaskjdl1k2naskjkdakj34ckhgkgkfyufyt8sa";

    // 서버에서 토큰을 발행하는 역할
    @Override
    public String createToken(String subjet, long ttlMillis) {
        if (ttlMillis < 0) {
            throw new RuntimeException(
                "Expiry time must be greater than Zero : [" + ttlMillis + "] ");

        }
        // 토큰을 서명하기 위해 사용할 알고리즘 선택
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
            .setSubject(subjet)
            .signWith(signingKey, signatureAlgorithm)
            .setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
            .compact();
    }

    // 비밀키로 토큰을 풀어 값을 가져오는 역할
   @Override
    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }
}