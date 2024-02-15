package site.junyo.minheegame.api.http;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.junyo.minheegame.api.http.dto.request.UserLoginRequest;
import site.junyo.minheegame.api.http.dto.request.UserSignUpRequest;
import site.junyo.minheegame.api.http.dto.response.CommonResponse;
import site.junyo.minheegame.api.http.dto.response.UserLoginResponse;
import site.junyo.minheegame.jwt.JwtService;
import site.junyo.minheegame.user.service.UserService;

import java.util.Map;

import static site.junyo.minheegame.api.http.util.Constant.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Slf4j
public class HttpController {

    private final UserService userService;
    private final JwtService jwtService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        userService.signUp(userSignUpRequest);
        return new ResponseEntity<>(new CommonResponse(0, "요청이 성공적으로 처리 됐습니다."), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {

        UserLoginResponse dto = userService.login(userLoginRequest);

        Map<String, String> payload = Map.of(PAYLOAD_KEY_SUB, dto.getUuid().toString(), PAYLOAD_KEY_NIC, dto.getNickname());
        String token = jwtService.createToken(payload);

        return ResponseEntity.ok()
                .header(HEADER_NAME_AUTHORIZATION, token)
                .body(dto);
    }
}
