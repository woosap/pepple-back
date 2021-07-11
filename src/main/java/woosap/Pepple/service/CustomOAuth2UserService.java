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
import woosap.Pepple.dto.oauth2.Oauth2Info;
import woosap.Pepple.dto.resolver.Oauth2ProviderResolver;
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
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Oauth2ProviderResolver oauth2ProviderResolver= new Oauth2ProviderResolver(provider, authenticatedUser.getAttributes());
        Oauth2Info userInfo = oauth2ProviderResolver.resolve();
        log.info("id is {}", userInfo.getId());
        log.info("imagePath is {}", userInfo.getImagePath());
        log.info("Nickname is {}", userInfo.getNickname());
//        userRepository.findOne()
        return authenticatedUser;
    }

}
