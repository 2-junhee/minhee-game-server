package site.junyo.minheegame.api.exception.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.junyo.minheegame.api.http.dto.response.CommonResponse;
import site.junyo.minheegame.api.exception.InvalidException;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {


}
