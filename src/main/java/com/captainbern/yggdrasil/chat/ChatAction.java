package com.captainbern.yggdrasil.chat;

public enum ChatAction {

    OPEN_FILE("open_file"),
    OPEN_LINK("open_url"),
    SUGGEST_COMMAND("suggest_command"),
    RUN_COMMAND("run_command"),
    SHOW_ITEM("show_item");

    private final String rawName;

    ChatAction(String rawName) {
          this.rawName = rawName;
    }

    @Override
    public String toString() {
        return this.rawName;
    }
}
