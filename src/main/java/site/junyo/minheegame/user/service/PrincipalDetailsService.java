package site.junyo.minheegame.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.junyo.minheegame.user.domain.PrincipalDetails;
import site.junyo.minheegame.user.domain.User;
import site.junyo.minheegame.user.repository.UserRepository;


@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        User user = userRepository.findByUuid(uuid);

        log.info("[loadUserByUsername] user = {} ", user);

        if (user == null) {
            throw new UsernameNotFoundException(uuid);
        } else {
            return new PrincipalDetails(user);
        }
    }
}
