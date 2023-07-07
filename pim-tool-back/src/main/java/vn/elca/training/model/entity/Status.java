package vn.elca.training.model.entity;

public enum Status {
    New("New"),
    Finished("Finished"),
    Inprogress("In progress"),
    Planned("Planned");

    private String status;

    Status(String status) {
        this.status = status;
    }

}
