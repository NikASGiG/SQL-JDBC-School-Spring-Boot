package ua.foxminded.nikasgig.sqljdbcschool.exception;

public class ConnectionFailedException extends RuntimeException {
    public ConnectionFailedException(String message) {
        super(message);
    }
}
