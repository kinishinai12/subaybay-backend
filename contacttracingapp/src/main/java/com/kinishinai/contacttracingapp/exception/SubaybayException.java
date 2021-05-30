package com.kinishinai.contacttracingapp.exception;

public class SubaybayException extends RuntimeException{
    public SubaybayException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SubaybayException(String exMessage) {
        super(exMessage);
    }
}
