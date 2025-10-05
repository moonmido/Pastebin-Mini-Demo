package com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String message) {
        super(message);
    }
}
