package vn.elca.training.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.elca.training.model.exception.EmployeeNotFoundException;
import vn.elca.training.model.exception.ProjectInfoException;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, Object>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("errors", errorMap);
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ProjectInfoException.class})
    public ResponseEntity<Map<String, Object>> handleProjectNumberException(ProjectInfoException ex) {
        Map<String, String> errorMap = ex.getErrorsMap();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("errors", errorMap);
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> responseMap = new HashMap<>();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("messages", ex.getMessage());

        responseMap.put("errors", errorMap);
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EmployeeNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("errors", ex.getErrors());
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }
}

