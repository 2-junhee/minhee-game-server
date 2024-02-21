package site.junyo.minheegame.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import site.junyo.minheegame.api.exception.DuplicateUserIdException;
import site.junyo.minheegame.api.http.dto.request.UserSignUpRequest;
import site.junyo.minheegame.user.domain.User;
import site.junyo.minheegame.user.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void signUp(UserSignUpRequest dto) {

        String id = dto.getId();
        if (userRepository.existsByUserId(id)) {
            throw new DuplicateUserIdException(-32, "이미 존재하는 아이디입니다.");
        }
        User user = User.builder()
                .uuid(UUID.randomUUID())
                .id(dto.getId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname()).build();
        userRepository.save(user);
    }


}
