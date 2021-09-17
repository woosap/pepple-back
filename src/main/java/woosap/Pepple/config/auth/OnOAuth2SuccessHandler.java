package woosap.Pepple.config.auth;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.util.UriEncoder;
import woosap.Pepple.dto.oauth2.Oauth2Info;
import woosap.Pepple.repository.UserRepository;
import woosap.Pepple.security.TokenService;
import woosap.Pepple.util.Constants;

@Component
@Slf4j
@RequiredArgsConstructor
public class OnOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        String notRegisteredUrl = Constants.REDIRECT_URL + "/register";
        String registeredUrl = Constants.REDIRECT_URL + "/";
        String redirectUrlWithParams;

        log.info("Success");
        if (response.isCommitted()) {
            log.error("이미 처리된 응답입니다.");
            return ;
        }

        Oauth2Info oAuth2User = (Oauth2Info) authentication.getPrincipal();

        if (userRepository.existsById(oAuth2User.getId())) {
            log.info("이미 가입한 아이디");
            String token = tokenService.createToken(oAuth2User.getId());
            redirectUrlWithParams = UriComponentsBuilder.fromUriString(registeredUrl)
                .queryParam("token", UriEncoder.encode(token))
                .build()
                .toUriString();
        } else {
            redirectUrlWithParams = UriComponentsBuilder.fromUriString(notRegisteredUrl)
                .queryParam("id", UriEncoder.encode(oAuth2User.getId()))
                .queryParam("image", UriEncoder.encode(oAuth2User.getImagePath()))
                .build()
                .toUriString();
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrlWithParams);
    }
}