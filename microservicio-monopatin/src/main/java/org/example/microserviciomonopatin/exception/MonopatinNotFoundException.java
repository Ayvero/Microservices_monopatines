package org.example.microserviciomonopatin.exception;

public class MonopatinNotFoundException extends RuntimeException {
    public MonopatinNotFoundException(String message) {
        super(message);
    }
}
