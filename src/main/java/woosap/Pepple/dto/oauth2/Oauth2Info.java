package woosap.Pepple.dto.oauth2;

import java.util.Map;

public abstract class Oauth2Info {
    private Map<String, Object> attributes;
    private String id;
    private String nickname;
    private String imagePath;

    public Oauth2Info(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public abstract String getId();

    public abstract String getNickname();

    public abstract String getImagePath();
}
