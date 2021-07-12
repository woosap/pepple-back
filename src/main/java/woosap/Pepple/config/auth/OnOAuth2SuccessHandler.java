package woosap.Pepple.config.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import woosap.Pepple.dto.SessionSaveInfo;
import woosap.Pepple.util.Constants;

@Component
@RequiredArgsConstructor
@Slf4j
public class OnOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpSession session;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        String detailRedirectUrl = Constants.REDIRECT_URL + "/register";
        String tokenRedirectUrl = Constants.REDIRECT_URL;
        String redirectUrlWithParams;

        log.info("Success");
        if (response.isCommitted()) {
            log.debug("이미 처리된 응답입니다.");
            return ;
        }
        /*
        만약 세션에 아이디가 저장이 되어있지 않으면 -> 인증에 성공 하고, 기존 디비에 저장이 되어있다는 것은 이미 가입유저 -> 토큰 발급
        세션에 아이디가 저장이 되어있다 -> 추가 정보를 받아야 한다.
         */

        if (session.getAttribute(Constants.SESSION_KEY) == null) {
            //TODO : get token
            // id ->  System.out.println(((DefaultOAuth2User)authentication.getPrincipal()).getAttribute("id").toString()); 이용할 것

        } else {
            // queryString으로 처리.
            SessionSaveInfo saveInfo = (SessionSaveInfo) session.getAttribute(Constants.SESSION_KEY);
            redirectUrlWithParams = UriComponentsBuilder.fromUriString(detailRedirectUrl)
                .queryParam("id", saveInfo.getUserId())
                .queryParam("nickname", saveInfo.getNickname())
                .queryParam("image", saveInfo.getImageUrl())
                .build()
                .toUriString();
            getRedirectStrategy().sendRedirect(request, response, redirectUrlWithParams);
        }

    }
}
