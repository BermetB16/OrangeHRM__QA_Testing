package com.learnaqa.exception;

public class PageNotFoundException extends RuntimeException{
    public PageNotFoundException(String message) {
        super(message);
    }
}
