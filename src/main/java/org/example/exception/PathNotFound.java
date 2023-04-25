package org.example.exception;

public class PathNotFound extends RuntimeException{
    public PathNotFound(String message) {
        super(message);
    }
}
