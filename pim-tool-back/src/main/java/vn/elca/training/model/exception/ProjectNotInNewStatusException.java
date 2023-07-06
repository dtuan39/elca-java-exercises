package vn.elca.training.model.exception;
//checked exception
public class ProjectNotInNewStatusException extends Exception {
    public ProjectNotInNewStatusException(String message) {
        super(message);
    }
}
