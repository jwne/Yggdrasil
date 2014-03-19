package com.captainbern.yggdrasil.tab;

import org.bukkit.plugin.Plugin;

public class TabView {

    public static final int LOWEST_PING = 0;
    public static final int LOW_PING = 1;
    public static final int NORMAL_PING = 2;
    public static final int HIGH_PING = 3;
    public static final int HIGHEST_PING = 5;

    private final Plugin plugin;

    // The default tab-view, 3 columns with 20 rows each
    private final String[][][] tabView = new String[20][20][20];

    public TabView(Plugin plugin) {
        this.plugin = plugin;
    }
}
