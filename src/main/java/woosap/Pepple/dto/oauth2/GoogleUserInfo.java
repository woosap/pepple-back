package woosap.Pepple.dto.oauth2;

import java.util.Map;

public class GoogleUserInfo extends Oauth2Info{

    public GoogleUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("sub").toString();
    }

    @Override
    public String getNickname() {
        return attributes.get("name").toString();
    }

    @Override
    public String getImagePath() {
        return attributes.get("picture").toString();
    }
}
