package woosap.Pepple.dto.resolver;

import java.util.Map;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import woosap.Pepple.dto.oauth2.GithubUserInfo;
import woosap.Pepple.dto.oauth2.GoogleUserInfo;
import woosap.Pepple.dto.oauth2.KakaoUserInfo;
import woosap.Pepple.dto.oauth2.Oauth2Info;
import woosap.Pepple.util.Constants;

public class Oauth2ProviderResolver {
    private String provider;
    public Map<String, Object> attributes;

    public Oauth2ProviderResolver(String provider, Map<String, Object> attributes) {
        this.provider = provider;
        this.attributes = attributes;
    }

    public Oauth2Info resolve() {
        if (this.provider.equals(Constants.GITHUB)) {
            return new GithubUserInfo(this.attributes);
        } else if (this.provider.equals(Constants.GOOGLE)) {
            return new GoogleUserInfo(this.attributes);
        } else if (this.provider.equals(Constants.KAKAO)) {
            return new KakaoUserInfo(this.attributes);
        } else {
            throw new OAuth2AuthenticationException("This provider is not supported");
        }
    }
}
