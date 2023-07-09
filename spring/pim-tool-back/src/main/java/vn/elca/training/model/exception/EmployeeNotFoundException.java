package vn.elca.training.model.exception;

import java.util.HashMap;
import java.util.Map;

public class EmployeeNotFoundException extends Exception {
    Map<Long, String> errors = new HashMap<>();

    public EmployeeNotFoundException() {
    }

    public void add(Long id) {
        errors.put(id, "This employee id " + id + " not found!");
    }

    public Map<Long, String> getErrors() {
        return this.errors;
    }
}
