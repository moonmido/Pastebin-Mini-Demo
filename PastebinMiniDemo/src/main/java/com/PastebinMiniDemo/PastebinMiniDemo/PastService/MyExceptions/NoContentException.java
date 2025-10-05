package com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions;

public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}
