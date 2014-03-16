package com.captainbern.common.threading;

import org.bukkit.Bukkit;

public class ThreadManager {

    private static Thread primaryThread;
    private static boolean useBukkit;

    private TPSMonitorTask monitorTask;

    public ThreadManager() {
        if(monitorTask == null) {
            monitorTask = new TPSMonitorTask();
        }
    }

    public static boolean isPrimaryThread() {
        if(useBukkit) {
            return Bukkit.isPrimaryThread();
        }
        return Thread.currentThread().equals(primaryThread);
    }

    public static void registerPrimaryThread() {
        primaryThread = Thread.currentThread();
    }
}
