package com.stussy.stussyclone20220929jaehyo.exception;

public class CustomInternalServerErrorException extends RuntimeException {

    public CustomInternalServerErrorException(String message) {
        super(message);
    }
}
