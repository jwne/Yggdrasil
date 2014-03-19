package com.captainbern.yggdrasil.nbt.exception;

public class NBTReadException extends NBTException {

    public NBTReadException() {}

    public NBTReadException(String message) {
        super(message);
    }

    public NBTReadException(Exception exception) {
        super(exception);
    }
}
