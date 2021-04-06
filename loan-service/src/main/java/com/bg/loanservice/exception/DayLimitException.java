package com.bg.loanservice.exception;

public class DayLimitException extends Exception{

    private static final long serialVersionUID = 1L;

    public DayLimitException(){super();}
    public DayLimitException(String errorMessage) {
        super(errorMessage);
    } 

}
