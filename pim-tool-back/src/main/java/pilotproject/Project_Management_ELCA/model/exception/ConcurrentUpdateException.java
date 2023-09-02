package pilotproject.Project_Management_ELCA.model.exception;

public class ConcurrentUpdateException extends RuntimeException{
    public ConcurrentUpdateException(String message) {
        super(message);
    }
}
