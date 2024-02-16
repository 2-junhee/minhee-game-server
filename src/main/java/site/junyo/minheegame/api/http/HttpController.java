package site.junyo.minheegame.api.http;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.junyo.minheegame.api.http.dto.request.UserSignUpRequest;
import site.junyo.minheegame.api.http.dto.response.CommonResponse;
import site.junyo.minheegame.member.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Slf4j
public class HttpController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponse> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        userService.signUp(userSignUpRequest);

        return new ResponseEntity<>(new CommonResponse(0, "요청이 성공적으로 처리 됐습니다."), HttpStatus.CREATED);
    }
}
