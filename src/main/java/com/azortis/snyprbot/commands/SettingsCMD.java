package com.azortis.snyprbot.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.CommandCategory;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.settings.GuildSettings;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SettingsCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(!event.isFromGuild()){
            event.getChannel().sendMessage(":x: **This command can only be used in guilds!**").queue();
            return;
        }
        Long guildId = event.getGuild().getIdLong();
        switch (args[0]){
            case "djOnly":
                GuildSettings djOnlySettings = SnyprBot.getSettingsManager().getGuildSettings(guildId, (Object... objects) -> {
                    GuildSettings djOnlySettings1 = (GuildSettings) objects[0];
                    MessageChannel channel = (MessageChannel) objects[1];
                    String djOnlyValue = (String) objects[2];
                    if(!Boolean.parseBoolean(djOnlyValue)){
                        channel.sendMessage(":x: **Invalid value!**").queue();
                        return;
                    }
                    if(djOnlySettings1.isDjOnly() == Boolean.valueOf(djOnlyValue)){
                        channel.sendMessage(":x: **Changed nothing, setting was already set to specified value!**").queue();
                        return;
                    }
                    djOnlySettings1.setDjOnly(Boolean.valueOf(djOnlyValue));
                    channel.sendMessage(":white_check_mark: **Successfully set`** `djOnly` **to** `" + djOnlyValue + "`").queue();
                    djOnlySettings1.save();
                }, event.getChannel(), args[1]);
                String value = args[1];
                if(!Boolean.parseBoolean(value)){
                    event.getChannel().sendMessage(":x: **Invalid value!**").queue();
                    return;
                }
                if(djOnlySettings.isDjOnly() == Boolean.valueOf(value)){
                    event.getChannel().sendMessage(":x: **Changed nothing, setting was already set to specified value!**").queue();
                    return;
                }
                djOnlySettings.setDjOnly(Boolean.valueOf(value));
                event.getChannel().sendMessage(":white_check_mark: **Successfully set`** `djOnly` **to** `" + value + "`").queue();
                djOnlySettings.save();
                break;
            case "djRoleId":
                GuildSettings djRoleSettings = SnyprBot.getSettingsManager().getGuildSettings(guildId, (Object... objects)-> {
                    GuildSettings djRoleSettings1 = (GuildSettings) objects[0];
                    MessageChannel channel = (MessageChannel) objects[1];
                    String djRoleValue = (String) objects[2];

                }, event.getChannel(), args[1]);
        }
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MODERATION;
    }
}
