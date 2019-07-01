package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.CommandCategory;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@SuppressWarnings("all")
public class PauseCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.isFromGuild()){
            Guild guild = event.getGuild();
            if(guild.getMember(event.getJDA().getSelfUser()).getVoiceState().getChannel() == null){
                event.getChannel().sendMessage(":x: **I'm not in a voice channel!**").queue();
                return;
            }
            if(event.getMember().getVoiceState().getChannel() == null){
                event.getChannel().sendMessage(":x: **You must be in my voice channel to do that!**").queue();
                return;
            }
            if(event.getMember().getVoiceState().getChannel() == guild.getMember(event.getJDA().getSelfUser()).getVoiceState().getChannel()){
                MusicManager musicManager = SnyprBot.getMusicManager();
                if(!musicManager.getGuildAudioManager(guild).getPlayer().isPaused()){
                    musicManager.getGuildAudioManager(guild).getPlayer().setPaused(true);
                    event.getChannel().sendMessage(":pause_button: **Pausing...**").queue();
                }else {
                    event.getChannel().sendMessage(":x: **You must be in the same voice channel as me!**").queue();
                }
            }
        }
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }
}
