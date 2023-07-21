package vn.elca.training.model.exception;

public class ProjectNotFoundException extends Exception{
    public ProjectNotFoundException() {
        super("Project not found");
    }

    public ProjectNotFoundException(Long id) {
        super(String.format("Project with id %d not found", id));
    }

    public ProjectNotFoundException(Integer projectNumber) {
        super(String.format("Project with number %d not found", projectNumber));
    }
}
