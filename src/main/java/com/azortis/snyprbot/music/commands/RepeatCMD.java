package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.music.MusicManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RepeatCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.getGuild() != null){
            Guild guild = event.getGuild();
            if(event.getMember().getVoiceState().getChannel() == guild.getMember(event.getJDA().getSelfUser()).getVoiceState().getChannel()){
                MusicManager musicManager = SnyprBot.getMusicManager();
                if(musicManager.getGuildAudioManager(guild).getTrackScheduler().isRepeat()){
                    musicManager.getGuildAudioManager(guild).getTrackScheduler().setRepeat(false);
                    event.getChannel().sendMessage(":repeat_one: **Disabled!**").queue();
                }else{
                    musicManager.getGuildAudioManager(guild).getTrackScheduler().setRepeat(true);
                    event.getChannel().sendMessage(":repeat_one: **Enabled!**").queue();
                }
            }
        }
    }
}
