package woosap.Pepple.dto.oauth2;

import java.util.Map;

public class GithubUserInfo extends Oauth2Info{

    public GithubUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Long getId() {
        return Long.parseLong(attributes.get("id").toString());
    }

    @Override
    public String getNickname() {
        return attributes.get("login").toString();
    }

    @Override
    public String getImagePath() {
        return attributes.get("avatar_url").toString();
    }
}
