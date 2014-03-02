package com.captainbern.common.threading;

public class ThreadManager {

    private TPSMonitorTask monitorTask;

    public ThreadManager() {
         if(monitorTask == null) {
             monitorTask = new TPSMonitorTask();
         }
    }
}
