package com.captainbern.common.protocol;

public enum Sender {

    CLIENT,
    SERVER;

    public ConnectionSide toConnectionSide() {
        switch (this) {
            case CLIENT :
                return ConnectionSide.CLIENT;
            case SERVER :
                return ConnectionSide.SERVER;
            default :
                return null;
        }
    }
}
