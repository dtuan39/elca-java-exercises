package vn.elca.training.model.entity;

public enum Role {

    DEV("Developer"),
    QA("Quality Agent"),
    GL("Group Leader"),
    PL("Project Leader");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
