package vn.elca.training.model.exception;

public class GroupNotFoundException extends Exception {

    public GroupNotFoundException() {
    }

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(long groupId) {
        super(String.format("Group with id %d not found", groupId));
    }
}
