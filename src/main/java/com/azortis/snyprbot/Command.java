package com.azortis.snyprbot;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {

    void execute(MessageReceivedEvent event, String[] args);

}
