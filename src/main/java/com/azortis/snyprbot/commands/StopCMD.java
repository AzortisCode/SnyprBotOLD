package com.azortis.snyprbot.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.CommandCategory;
import com.azortis.snyprbot.SnyprBot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class StopCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(SnyprBot.getConfig().getOwnerIDs().contains(event.getAuthor().getIdLong())){
            event.getChannel().sendMessage(":mailbox_with_no_mail: **Good bye!**").queue();
            event.getJDA().shutdown();
            System.exit(0);
        }else {
            event.getChannel().sendMessage(":x: **Only the bot owner(s) can execute this command!**").queue();
        }
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.BOT_OWNER;
    }
}
