package com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions;

public class CustomUrlNotValidException extends RuntimeException {
    public CustomUrlNotValidException(String message) {
        super(message);
    }
}
