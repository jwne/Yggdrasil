package com.captainbern.common.threading;

import java.util.LinkedList;

public class TPSMonitorTask implements Runnable {

    private long lastTime = getMilis();
    private LinkedList<Double> times = new LinkedList<Double>();

    public TPSMonitorTask() {}

    @Override
    public void run() {
        long current = getMilis();
        double seconds = (current - this.lastTime) / 1000.0D; // to seconds

        if(seconds == 0) {
            seconds = 1D;
        }

        int interval = 40;
        double tps = interval / seconds;

        int maxEntries = 10;
        if(this.times.size() >= maxEntries) {
            this.times.pop();
        }

        this.times.add(tps);

        this.lastTime = current;
    }

    public final synchronized double getAverageTPS() {
        if(this.times.size() > 0) {
            double all = 0;
            for(Double d : this.times) {
                all += d.floatValue();
            }
            return all / this.times.size();
        }
        return -1.0;
    }

    public final synchronized double getLastTPS() {
        if(this.times.size() > 0) {
            return this.times.getLast();
        }
        return -1.0;
    }

    public final synchronized long getMilis() {
        return System.currentTimeMillis();
    }

    public final synchronized void reset() {
        double last = getAverageTPS();
        this.times.clear();
        this.times.add(last);
    }
}
