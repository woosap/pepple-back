package woosap.Pepple.service;

import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import woosap.Pepple.dto.oauth2.KakaoUserInfo;
import woosap.Pepple.repository.UserRepository;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User authenticatedUser =  super.loadUser(userRequest);
        KakaoUserInfo info = new KakaoUserInfo(authenticatedUser.getAttributes());
        log.info("id is {}", info.getId());
        log.info("imagePath is {}", info.getImagePath());
        log.info("Nickname is {}", info.getNickname());
//        userRepository.findOne()
        return authenticatedUser;
    }

}
