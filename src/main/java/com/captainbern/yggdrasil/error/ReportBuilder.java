package com.captainbern.yggdrasil.error;

public class ReportBuilder {

    private Throwable throwable;

    private ReportBuilder() {}

    public ReportBuilder(Throwable throwable) {
          this.throwable = throwable;
    }

    public Throwable getThrowable()  {
        return this.throwable;
    }
}
