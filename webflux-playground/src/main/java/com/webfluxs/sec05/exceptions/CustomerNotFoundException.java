package com.webfluxs.sec05.exceptions;

public class CustomerNotFoundException extends RuntimeException{

    private static final String MESSAGE="Customer [id=%id] is not found";

    public CustomerNotFoundException(Integer id){
//        super(MESSAGE.formatted(id));
        super("Customer with id %s not found".formatted(id)); // if id is a string

    }
}
