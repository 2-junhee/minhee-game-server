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

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUserId(username);

        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException(username);
        } else {
            return new PrincipalDetails(userOpt.get());
        }
    }
}
