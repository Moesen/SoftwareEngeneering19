package dtu.library.app;

public class OperationNotAllowedException extends Exception {
    private String message;

    public OperationNotAllowedException(String message) {
        super(message);
    }

}
