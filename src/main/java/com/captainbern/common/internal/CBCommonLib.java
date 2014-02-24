package com.captainbern.common.internal;

import com.captainbern.common.ModuleLogger;
import com.captainbern.common.server.CommonServer;

public class CBCommonLib {

    public static final ModuleLogger LOGGER = new ModuleLogger("CBCommonLib");
    public static final ModuleLogger LOGGER_REFLECTION = LOGGER.getModule("Reflection");

    private static CBCommonLib INSTANCE;

    public static CBCommonLib getInstance() {
        if(INSTANCE == null) {
            throw new RuntimeException("Instance is NULL!");
        }
        return INSTANCE;
    }

    public CBCommonLib(CommonServer server) {

    }
}

