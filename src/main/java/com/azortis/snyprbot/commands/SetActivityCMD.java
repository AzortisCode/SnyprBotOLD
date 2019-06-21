package com.azortis.snyprbot.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.CommandCategory;
import com.azortis.snyprbot.SnyprBot;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

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
                event.getJDA().getPresence().setGame(Game.of(Game.GameType.DEFAULT, "snyprbot.xyz"));
                event.getChannel().sendMessage(":white_check_mark: **Set my activity to:** `default settings`").queue();
            }
            switch (args[0]){
                case "-streaming":
                    event.getJDA().getPresence().setGame(Game.of(Game.GameType.STREAMING, activity));
                    event.getChannel().sendMessage(":white_check_mark: **Set my activity to:** `" + activity + "`").queue();
                    break;
                case "-watching":
                    event.getJDA().getPresence().setGame(Game.of(Game.GameType.WATCHING, activity));
                    event.getChannel().sendMessage(":white_check_mark: **Set my activity to:** `" + activity + "`").queue();
                    break;
                case "-listening":
                    event.getJDA().getPresence().setGame(Game.of(Game.GameType.LISTENING, activity));
                    event.getChannel().sendMessage(":white_check_mark: **Set my activity to:** `" + activity + "`").queue();
                    break;
                default:
                    event.getJDA().getPresence().setGame(Game.of(Game.GameType.DEFAULT, activity));
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
