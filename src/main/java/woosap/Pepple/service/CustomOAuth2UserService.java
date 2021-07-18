package woosap.Pepple.service;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import woosap.Pepple.dto.SessionSaveInfo;
import woosap.Pepple.dto.oauth2.Oauth2Info;
import woosap.Pepple.util.resolver.Oauth2ProviderResolver;
import woosap.Pepple.entity.User;
import woosap.Pepple.repository.UserRepository;
import woosap.Pepple.util.Constants;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final HttpSession session;

    public CustomOAuth2UserService(UserRepository userRepository,
        HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User authenticatedUser =  super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Oauth2ProviderResolver oauth2ProviderResolver= new Oauth2ProviderResolver(provider, authenticatedUser.getAttributes());
        Oauth2Info userInfo = oauth2ProviderResolver.resolve();
        saveOrToGetAdditionalInfo(userInfo);
        return authenticatedUser;
    }

    private void saveOrToGetAdditionalInfo(Oauth2Info userInfo) {
        Optional<User> user = userRepository.findById(userInfo.getId());
        if (user.isEmpty()) {
            // session을 사용하는 이유 - 추가 정보를 받을 때, 기존에 소셜로그인을 통해서 들어왔다는 것을 증명해야 하기 때문에
            session.setAttribute(Constants.SESSION_KEY, new SessionSaveInfo(userInfo.getId(), userInfo.getImagePath()));
            // 추후에 가입후 이 세션을 제거해야 한다.
        }

    }

}
