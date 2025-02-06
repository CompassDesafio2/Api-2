package com.javeiros.microserviceB.exception;

public class DataBaseException extends RuntimeException  {
    public DataBaseException(String message) {
        super(message);
    }
}
