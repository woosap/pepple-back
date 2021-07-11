package woosap.Pepple.config.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import woosap.Pepple.util.Constants;

@Component
public class OnOAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private String redirectUrl = Constants.REDIRECT_URL;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {


        redirectUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                        .queryParam("error", exception.getLocalizedMessage())
                        .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
