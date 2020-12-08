package com.miracle.payment.common.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidTokenException extends RuntimeException{
    String actualToken;

    @Override
    public String toString(){
        return "Invalid Token--" + actualToken;
    }
}
