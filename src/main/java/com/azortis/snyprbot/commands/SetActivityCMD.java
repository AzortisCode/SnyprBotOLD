package com.azortis.snyprbot.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.CommandCategory;
import com.azortis.snyprbot.SnyprBot;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SetActivityCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(SnyprBot.getConfig().getOwnerIDs().contains(event.getAuthor().getIdLong())){
            StringBuilder stringBuilder = new StringBuilder();
            for (String arg : args){
                if(!arg.startsWith("-"))stringBuilder.append(arg).append(" ");
            }
            String activity = stringBuilder.toString().trim();
            if (activity.equals("")) {
                event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.DEFAULT, "snyprbot.xyz"));
                event.getChannel().sendMessage(":white_check_mark: **Set my activity to:** `default settings`").queue();
            }
            switch (args[0]){
                case "-streaming":
                    event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, activity));
                    event.getChannel().sendMessage(":white_check_mark: **Set my activity to:** `" + activity + "`").queue();
                    break;
                case "-watching":
                    event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.WATCHING, activity));
                    event.getChannel().sendMessage(":white_check_mark: **Set my activity to:** `" + activity + "`").queue();
                    break;
                case "-listening":
                    event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.LISTENING, activity));
                    event.getChannel().sendMessage(":white_check_mark: **Set my activity to:** `" + activity + "`").queue();
                    break;
                default:
                    event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.DEFAULT, activity));
                    event.getChannel().sendMessage(":white_check_mark: **Set my activity to:** `" + activity + "`").queue();
                    break;
            }
        }else {
            event.getChannel().sendMessage(":x: **Only the bot owner(s) can execute this command!**").queue();
        }
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.BOT_OWNER;
    }

}
