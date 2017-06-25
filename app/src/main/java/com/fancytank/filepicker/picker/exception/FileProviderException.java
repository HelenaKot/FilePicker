package com.fancytank.filepicker.picker.exception;

public class FileProviderException extends Exception {
    private final ExceptionType exceptionType;

    public FileProviderException(ExceptionType exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    public FileProviderException(String message, ExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}
