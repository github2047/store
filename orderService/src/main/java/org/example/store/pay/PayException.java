package org.example.store.pay;

public class PayException extends RuntimeException{
    public PayException(String message) {
        super(message);
    }
    public PayException(String message,Throwable e) {
        super(message,e);
    }
}
