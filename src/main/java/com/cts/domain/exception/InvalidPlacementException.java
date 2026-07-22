package com.cts.domain.exception;

public class InvalidPlacementException extends RuntimeException {
    public InvalidPlacementException(String reason) {
        super(reason);
    }
}
