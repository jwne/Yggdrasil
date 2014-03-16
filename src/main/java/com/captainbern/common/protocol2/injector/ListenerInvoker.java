package com.captainbern.common.protocol2.injector;

public class ListenerInvoker {

    private final ChannelPipelineInjector channelPipelineInjector;

    public ListenerInvoker(ChannelPipelineInjector injector) {
        this.channelPipelineInjector = injector;
    }

    public ChannelPipelineInjector getInjector() {
        if(this.channelPipelineInjector == null) {
            throw new IllegalStateException("Well this is embarrassing ChannelPipelineInjector is NULL!");
        }
        return this.channelPipelineInjector;
    }
}
