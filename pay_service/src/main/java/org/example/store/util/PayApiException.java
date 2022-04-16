package org.example.store.util;

public class PayApiException extends PayException {

    public PayApiException(int code, String message) {
        super(code, message);
    }
}
