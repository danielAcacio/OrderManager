package pt.com.sibs.order.manager.core.exceptions;

public class DataIntegrityException extends RuntimeException{
    public DataIntegrityException(String message) {
        super(message);
    }
}
