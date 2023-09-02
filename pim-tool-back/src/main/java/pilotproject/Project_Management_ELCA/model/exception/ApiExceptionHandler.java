package pilotproject.Project_Management_ELCA.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ProjectNumberExistedException.class)
    public ResponseEntity handlerDuplicatedProjectNumber(ProjectNumberExistedException projectNumberExistedException){
        return new ResponseEntity(projectNumberExistedException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConcurrentUpdateException.class)
    public ResponseEntity handleConcurrentUpdate(ConcurrentUpdateException concurrentUpdateException){
        return new ResponseEntity(concurrentUpdateException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
