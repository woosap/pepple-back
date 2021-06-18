package woosap.guide.service;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import woosap.guide.controller.UserController;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String password;
        if ((password = UserController.repository.get(username)) != null) {
            return new User(username, password, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User does not exist");
        }

    }
}
