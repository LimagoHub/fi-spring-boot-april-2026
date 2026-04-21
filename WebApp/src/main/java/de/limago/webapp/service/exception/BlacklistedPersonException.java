package de.limago.webapp.service.exception;

public class BlacklistedPersonException extends RuntimeException {
    public BlacklistedPersonException(String message) {
        super(message);
    }
}
