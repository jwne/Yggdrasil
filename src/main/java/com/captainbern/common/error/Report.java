package com.captainbern.common.error;

public class Report {

    private String header;
    private String basicMessage;
    private String detailedMessage;
    private Throwable throwable;

    public Report(String header, String basicMessage, String detailedMessage) {
        this(header, basicMessage, detailedMessage, null);
    }

    public Report(String header, String basicMessage, String detailedMessage, Throwable throwable) {
        this.header = header;
        if(basicMessage != null) {
            this.basicMessage = basicMessage;
        } else {
            if(throwable != null) {
                this.basicMessage = throwable.getLocalizedMessage();
            } else {
                this.basicMessage = "";
            }
        }
        this.detailedMessage = detailedMessage;
        this.throwable = throwable;
    }

    public Report(String header, Throwable throwable) {
        this(header, null, null, throwable);
    }

    public String getHeader() {
        return this.header;
    }

    public String getBasicMessage() {
        return this.basicMessage;
    }

    public String getDetailedMessage() {
        return this.detailedMessage;
    }

    public Throwable getErrorException() {
        return this.throwable;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setBasicMessage(String basicMessage) {
        this.basicMessage = basicMessage;
    }

    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }

    public void setErrorException(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(header != null) {
            stringBuilder.append(header);
        }

        stringBuilder.append(basicMessage);

        if(detailedMessage == null) {
            stringBuilder.append(throwable.getMessage());
        } else {
            stringBuilder.append(detailedMessage);
        }

        return stringBuilder.toString();
    }
}
