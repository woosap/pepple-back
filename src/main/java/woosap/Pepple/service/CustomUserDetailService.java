package woosap.Pepple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import woosap.Pepple.dto.oauth2.CustomUserDetails;
import woosap.Pepple.entity.User;
import woosap.Pepple.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    public final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User foundUser = userRepository.getById(userId);
        return CustomUserDetails.of(foundUser);
    }
}
