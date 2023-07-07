package pilotproject.Project_Management_ELCA.model.exception;

public class ProjectNumberExistedException extends RuntimeException {
    public ProjectNumberExistedException(String message) {
        super(message);
    }
}
