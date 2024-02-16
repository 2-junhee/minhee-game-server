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
import site.junyo.minheegame.api.http.dto.response.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        if (fieldErrors.size() == 1) {
            FieldError fieldError = fieldErrors.get(0);
            String fieldName = fieldError.getField();
            int errorCode = getErrorCodeForField(fieldName);
            String errorMessage = fieldError.getDefaultMessage();

            return new ResponseEntity<>(new CommonResponse(errorCode, errorMessage), HttpStatus.BAD_REQUEST);
        }

        List<String> errors = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResponse(-28, "다중 유효성 검사 실패", errors), HttpStatus.BAD_REQUEST);
    }

    private int getErrorCodeForField(String fieldName) {

        return switch (fieldName) {
            case "id" -> -22;
            case "password" -> -23;
            case "nickname" -> -24;
            default -> -28;
        };
    }

    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<CommonResponse> handlerDuplicateUserIdException(DuplicateUserIdException ex) {
        return new ResponseEntity<>(new CommonResponse(ex.getCode(), ex.getMessage()), HttpStatus.CONFLICT);
    }


}
