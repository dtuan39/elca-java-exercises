package vn.elca.training.model.exception;

import java.util.HashMap;
import java.util.Map;

public class ProjectInfoException extends Exception {
    Map<String, String> errorsMap = new HashMap<>();

    public ProjectInfoException() {
    }

    public ProjectInfoException(String msg) {
        super(msg);
    }

    public void addErrors(String field, String msg) {
        errorsMap.put(field, msg);
    }

    public Map<String, String> getErrorsMap() {
        return errorsMap;
    }
}
