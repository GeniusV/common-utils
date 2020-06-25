package com.geniusver.util;

/**
 * Created by GeniusV on 6/20/20.
 */
public class MultiThradFileProcessException extends  RuntimeException{
    public MultiThradFileProcessException() {
        super();
    }

    public MultiThradFileProcessException(String message) {
        super(message);
    }

    public MultiThradFileProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultiThradFileProcessException(Throwable cause) {
        super(cause);
    }

    protected MultiThradFileProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
