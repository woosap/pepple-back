package woosap.Pepple.dto.oauth2;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;

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
