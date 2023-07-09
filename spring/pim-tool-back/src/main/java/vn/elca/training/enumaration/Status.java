package vn.elca.training.enumaration;

public enum Status {
    NEW("NEW"),
    PLA("PLA"),
    INP("INP"),
    FIN("FIN");
    //New, Planned, In progress, Finished

    private final String projectStatus;

    Status(String status) {
        this.projectStatus = status;
    }

    public String getStatus() {
        return this.projectStatus;
    }


}
