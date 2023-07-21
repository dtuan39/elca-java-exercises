package vn.elca.training.model.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException() {
        super("Project not found");
    }
}
