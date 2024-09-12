package com.inventory.manager.ExcepcionesPersonalizadas;

public class DataBaseException extends RuntimeException{
    public DataBaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
