package woosap.Pepple.dto.oauth2;

import java.util.Map;

public abstract class Oauth2Info {
    public Map<String, Object> attributes;
    public Long id;
    public String nickname;
    public String imagePath;

    public Oauth2Info(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public abstract Long getId();

    public abstract String getNickname();

    public abstract String getImagePath();
}
