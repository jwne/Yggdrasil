package com.captainbern.yggdrasil.utils;

public enum OS {
    /**
     * Windows
     */
    WINDOWS_NT("Windows NT"),
    WINDOWS_95("Windows 95"),
    WINDOWS_98("Windows 98"),
    WINDOWS_2000("Windows 2000"),
    WINDOWS_VISTA("Windows Vista"),
    WINDOWS_7("Windows 7"),
    WINDOWS_OTHER("Windows"),

    /**
     * Linux
     * (Most of them need some "look at", since I'm not sure about the names)
     */
    LINUX("Linux"),

    /**
     * Unix
     */
    MAC("Mac OS X", "Darwin"),

    OTHER("");

    final String[] names;

    OS(String... names) {
        this.names = names;
    }

    public boolean isWindowsBased() {
        return ordinal() <= WINDOWS_OTHER.ordinal();
    }

    public boolean isLinuxBased() {
        return ordinal() >= LINUX.ordinal() && ordinal() <= LINUX.ordinal();
    }

    public boolean isUnixBased() {
        return ordinal() >= MAC.ordinal() && ordinal() <= MAC.ordinal();
    }

    public String getOsName() {
        return StringUtil.join(this.names, "/");
    }

    public String getKernelVersion() {
        return System.getProperty("os.version");
    }

    public boolean is64BitJava() {
        return System.getProperty("os.arch").contains("64");
    }

    /**
     * Hehehe
     * @return
     */
    public boolean isCool() {
        return isLinuxBased();
    }

    public static OS get(String osName) {
        osName = osName.toLowerCase();
        for (final OS os : values()) {
            for (final String name : os.names) {
                if (osName.contains(name.toLowerCase())) {
                    return os;
                }
            }
        }
        throw new IllegalStateException("Failed to get the OS!");
    }

    private static OS current;

    /**
     * @return OS on which this JVM is running
     */
    public static OS get() {
        if (current == null) {
            current = get(System.getProperty("os.name"));
        }
        return current;
    }
}
