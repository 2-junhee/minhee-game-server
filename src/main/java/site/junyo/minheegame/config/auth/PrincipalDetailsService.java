package site.junyo.minheegame.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.junyo.minheegame.member.domain.User;
import site.junyo.minheegame.member.repository.UserRepository;

import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String randomUUID = UUID.randomUUID().toString();

        User user = userRepository.findByUuid(randomUUID);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return new PrincipalDetails(user);
        }
    }
}
