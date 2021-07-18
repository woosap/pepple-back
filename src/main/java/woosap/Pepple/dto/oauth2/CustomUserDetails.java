package woosap.Pepple.dto.oauth2;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import woosap.Pepple.entity.User;
import woosap.Pepple.entity.type.Job;

public class CustomUserDetails implements UserDetails, OAuth2User {

    private String id;
    private String nickname;
    private String imageUrl;
    private Job job;
    private String profile;
    private List<String> snsList;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomUserDetails(String id, String nickname, String imageUrl,
                                Job job, String profile, List<String> snsList,
                                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.job = job;
        this.profile = profile;
        this.snsList = snsList;
        this.authorities = authorities;
    }

    public static CustomUserDetails of(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new CustomUserDetails(
            user.getUserId(),
            user.getNickname(),
            user.getImageUrl(),
            user.getJob(),
            user.getProfile(),
            user.getSnsList(),
            authorities
        );
    }


    @Override
    public String getName() {
        return id;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return id;
    }


    public String getNickname() {
        return nickname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Job getJob() {
        return job;
    }

    public String getProfile() {
        return profile;
    }

    public List<String> getSnsList() {
        return snsList;
    }
}
