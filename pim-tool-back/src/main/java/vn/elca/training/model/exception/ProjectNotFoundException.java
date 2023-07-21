package vn.elca.training.model.exception;

public class ProjectNotFoundException extends Exception{
    public ProjectNotFoundException() {
        super("Project not found");
    }
}
