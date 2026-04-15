package yono.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import yono.entity.UserAccount;
import yono.repository.UserAccountRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAccount user = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (!user.isActive()) {
            throw new UsernameNotFoundException("Account is closed");
        }

        return new User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}