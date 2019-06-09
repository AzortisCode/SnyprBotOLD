package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.music.MusicManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ResumeCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.getGuild() != null){
            Guild guild = event.getGuild();
            if(event.getMember().getVoiceState().getChannel() == guild.getMember(event.getJDA().getSelfUser()).getVoiceState().getChannel()){
                MusicManager musicManager = SnyprBot.getMusicManager();
                if(musicManager.getGuildAudioManager(guild).getPlayer().isPaused()){
                    musicManager.getGuildAudioManager(guild).getPlayer().setPaused(false);
                    event.getChannel().sendMessage(":arrow_forward: **Resuming...**").queue();
                }else {
                    event.getChannel().sendMessage(":x: **I'm already playing!**").queue();
                }
            }
        }
    }
}
