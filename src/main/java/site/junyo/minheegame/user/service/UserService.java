package site.junyo.minheegame.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import site.junyo.minheegame.api.http.dto.request.UserLoginRequest;
import site.junyo.minheegame.api.http.dto.request.UserSignUpRequest;
import site.junyo.minheegame.api.http.dto.response.UserLoginResponse;
import site.junyo.minheegame.user.domain.User;
import site.junyo.minheegame.user.exception.InvalidLoginInfoException;
import site.junyo.minheegame.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static site.junyo.minheegame.user.util.ResponseInfo.LOGIN_FAIL;
import static site.junyo.minheegame.user.util.ResponseInfo.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void signUp(UserSignUpRequest dto) {

        User user = User.builder()
                .uuid(UUID.randomUUID())
                        .id(dto.getId())
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                        .nickname(dto.getNickname()).build();

        userRepository.save(user);

    }

    public UserLoginResponse login(UserLoginRequest dto) {

        String id = dto.getId();
        String password = dto.getPassword();

        Optional<User> userOpt = userRepository.findByUserId(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (checkPassword(password, user.getPassword())) {
                return UserLoginResponse.builder()
                        .code(SUCCESS.getCode())
                        .msg(SUCCESS.getMsg())
                        .nickname(user.getNickname())
                        .uuid(user.getUuid())
                        .build();
            }
        }
        throw new InvalidLoginInfoException(LOGIN_FAIL.getCode(), LOGIN_FAIL.getMsg());
    }

    private boolean checkPassword(String providedPassword, String encryptedPassword) {

        return bCryptPasswordEncoder.matches(providedPassword, encryptedPassword);
    }
}
