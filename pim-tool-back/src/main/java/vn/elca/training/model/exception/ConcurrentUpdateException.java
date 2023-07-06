package vn.elca.training.model.exception;

public class ConcurrentUpdateException extends RuntimeException{
    public ConcurrentUpdateException(String message) {
        super(message);
    }
}
