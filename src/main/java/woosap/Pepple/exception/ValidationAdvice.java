package woosap.Pepple.exception;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ValidationAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        final BindingResult br = ex.getBindingResult();
        final java.util.List<FieldError> fieldErrors = br.getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            log.error("field: {}", fieldError.getField());
            log.error("message: {}", fieldError.getDefaultMessage());
            log.error("rejectedValue: {}", fieldError.getRejectedValue());
        }

        return fieldErrors
            .stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());

    }

}
