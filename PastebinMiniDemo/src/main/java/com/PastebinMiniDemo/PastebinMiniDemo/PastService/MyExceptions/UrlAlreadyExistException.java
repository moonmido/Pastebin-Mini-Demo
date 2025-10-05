package com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions;

public class UrlAlreadyExistException extends RuntimeException {
    public UrlAlreadyExistException(String message) {
        super(message);
    }
}
