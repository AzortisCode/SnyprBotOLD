package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.music.MusicManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LeaveCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.getGuild() != null){
            Guild guild = event.getGuild();
            if(guild.getMember(event.getJDA().getSelfUser()).getVoiceState().getChannel()!= null){
                event.getChannel().sendMessage(":mailbox: **Leaving now... Bye!**").queue();
                MusicManager musicManager = SnyprBot.getMusicManager();
                musicManager.closeAudioConnection(guild);
                musicManager.getGuildAudioManager(guild).getPlayer().stopTrack();
                musicManager.getGuildAudioManager(guild).getTrackScheduler().emptyQueue();
                musicManager.getGuildAudioManager(guild).getTrackScheduler().unbindTextChannel();
            }else{
                event.getChannel().sendMessage(":x: **I'm not in a voice channel!**").queue();
            }
        }
    }
}
