package woosap.Pepple.service;

import io.agora.media.RtcTokenBuilder;
import io.agora.media.RtcTokenBuilder.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import woosap.Pepple.dto.TokenDTO;
import woosap.Pepple.dto.agora.AgoraTokenDTO;

@Service
@Slf4j
public class AgoraService {
    @Value("${agora.appId}")
    private String agoraAppId;

    @Value("${agora.appCertificate}")
    private String agoraCertificate;

    private final int expirationTimeSeconds = 3600 * 6; // 6 hours

    public TokenDTO getAgoraAuthToken(AgoraTokenDTO agoraTokenDTO) {
        log.info("Token dto is {}", agoraTokenDTO);
        RtcTokenBuilder tokenBuilder = new RtcTokenBuilder();
        int timeStamp = (int)(System.currentTimeMillis() / 1000 + expirationTimeSeconds);
        String result = tokenBuilder.buildTokenWithUserAccount(
            agoraAppId, agoraCertificate, agoraTokenDTO.getChannelName(), agoraTokenDTO.getUserAccount()
            , Role.Role_Publisher, timeStamp);
        return new TokenDTO(result);
    }
}
