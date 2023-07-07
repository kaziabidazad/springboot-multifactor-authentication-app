package com.duke.mfa.poc.exception;

/**
 * @author Kazi
 */
public class NotImplementedException extends RuntimeException {

    private static final long serialVersionUID = 4617220223330266249L;

    public NotImplementedException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public NotImplementedException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public NotImplementedException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public NotImplementedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
