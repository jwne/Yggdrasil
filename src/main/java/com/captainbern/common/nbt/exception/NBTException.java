package com.captainbern.common.nbt.exception;

public class NBTException extends RuntimeException {

    public NBTException() {}

    public NBTException(String message) {
        super(message);
    }

    public NBTException(Exception exception) {
        super(exception);
    }
}
