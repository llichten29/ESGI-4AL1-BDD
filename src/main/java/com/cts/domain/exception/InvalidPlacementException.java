package com.cts.domain.exception;

public class InvalidPlacementException extends RuntimeException {
    private final String reason;

    public InvalidPlacementException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
