package site.junyo.minheegame.api.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.junyo.minheegame.api.exception.DuplicateUserIdException;
import site.junyo.minheegame.api.http.dto.response.CommonResponse;
import site.junyo.minheegame.api.http.dto.response.InvalidResponse;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();

        log.info("erros ={}", errors);

        return new ResponseEntity<>(new InvalidResponse(-28,"여러 항목 유효성 검사 실패 ", errors), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<CommonResponse> handlerDuplicateUserIdException(DuplicateUserIdException ex) {
        return new ResponseEntity<>(new CommonResponse(ex.getCode(), ex.getMessage()), HttpStatus.CONFLICT);
    }

}
