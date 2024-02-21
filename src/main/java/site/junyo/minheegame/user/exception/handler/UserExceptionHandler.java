package site.junyo.minheegame.user.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.junyo.minheegame.api.http.dto.response.CommonResponse;
import site.junyo.minheegame.user.exception.InvalidLoginInfoException;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(InvalidLoginInfoException.class)
    public ResponseEntity<CommonResponse> invalidUserInfo(InvalidLoginInfoException e) {

        CommonResponse response = new CommonResponse(e.getCode(), e.getMsg());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
