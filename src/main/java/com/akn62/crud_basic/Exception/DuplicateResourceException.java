package com.akn62.crud_basic.Exception;

public class DuplicateResourceException  extends RuntimeException{

    public DuplicateResourceException(String msg){
        super(msg);
    }
}
