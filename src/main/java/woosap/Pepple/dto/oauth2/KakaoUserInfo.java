package woosap.Pepple.dto.oauth2;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;

public class KakaoUserInfo extends Oauth2Info{

    public Map<String, Object> accountAttributes;
    public Map<String, Object> profileDetail;

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
        this.accountAttributes = (Map<String, Object>) attributes.get("kakao_account");
        this.profileDetail = (Map<String, Object>) accountAttributes.get("profile");
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getNickname() {
        Optional<Entry<String, Object>> foundEntry;
        foundEntry = this.profileDetail.entrySet()
            .stream()
            .filter((entrySet) ->  entrySet.getKey().equals("nickname"))
            .findAny();
        return foundEntry.map(attribute -> attribute.getValue().toString())
            .orElse(null);
    }

    @Override
    public String getImagePath() {
        Optional<Entry<String, Object>> foundEntry;
        foundEntry = this.profileDetail.entrySet()
            .stream()
            .filter((entrySet) ->  entrySet.getKey().equals("profile_image_url"))
            .findFirst();
        return foundEntry.map(attribute -> attribute.getValue().toString())
            .orElse(null);
    }

    @Override
    public <A> A getAttribute(String name) {
        return super.getAttribute(name);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return this.getId();
    }
}
