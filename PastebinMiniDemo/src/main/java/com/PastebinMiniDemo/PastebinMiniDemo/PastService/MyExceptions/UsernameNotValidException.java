package com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions;

public class UsernameNotValidException extends RuntimeException {
    public UsernameNotValidException(String message) {
        super(message);
    }
}
