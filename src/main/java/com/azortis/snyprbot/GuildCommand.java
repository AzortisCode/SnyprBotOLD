package com.azortis.snyprbot;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public interface GuildCommand {

    void execute(GuildMessageReceivedEvent event, String[] args);

}
