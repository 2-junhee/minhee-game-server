package site.junyo.minheegame.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.junyo.minheegame.api.http.dto.request.UserSignUpRequest;
import site.junyo.minheegame.member.domain.User;
import site.junyo.minheegame.member.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUp(UserSignUpRequest dto) {

        User user = User.builder()
                        .id(dto.getId())
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                        .nickname(dto.getNickname()).build();

        userRepository.save(user);
    }
}
