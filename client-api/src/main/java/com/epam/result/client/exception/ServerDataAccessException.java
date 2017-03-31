package com.epam.result.client.exception;

/**
 * @author  Bogdan Viktor
 */
public class ServerDataAccessException extends RuntimeException{
    public ServerDataAccessException(String message) {
        super(message);
    }

    public ServerDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
