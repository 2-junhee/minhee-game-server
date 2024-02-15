package site.junyo.minheegame.exception.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.junyo.minheegame.api.http.dto.response.CommonResponse;
import site.junyo.minheegame.exception.InvalidException;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidException.class)
    public CommonResponse invalidRequest(MethodArgumentNotValidException e, BindingResult bindingResult) {
        FieldError fieldError = bindingResult.getFieldError();
        Objects.requireNonNull(fieldError);

        String[] s = fieldError.getDefaultMessage().split(":");
    return new CommonResponse(2, "d");
    }

}
