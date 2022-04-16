package org.example.store.util;

public class PayException extends RuntimeException {
    private int code;

    public int getCode() {
        return code;
    }

    public PayException(String message) {
        this(-1, message);
    }

    public PayException(int code, String message) {
        super(message);
        this.code = code;
    }
}
