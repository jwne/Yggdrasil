package com.captainbern.common.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatMessage {

    private final List<MessageObject> messageObjectList = new ArrayList<MessageObject>();
    private String jsonString;

    public ChatMessage() {

    }

    public ChatMessage addAction(ChatAction action, Object metadata) {
        return this;
    }
}
