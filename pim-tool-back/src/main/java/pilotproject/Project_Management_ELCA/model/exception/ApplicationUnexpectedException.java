package pilotproject.Project_Management_ELCA.model.exception;

public class ApplicationUnexpectedException extends RuntimeException {
    public ApplicationUnexpectedException(Throwable e) {
        super("Unexpected exception", e);
    }
}
