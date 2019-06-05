package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.music.MusicManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SkipCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.getGuild() != null){
            MusicManager musicManager = SnyprBot.getMusicManager();
            if(event.getMember().getVoiceState().getChannel() == event.getGuild().getMember(event.getJDA().getSelfUser()).getVoiceState().getChannel()){
                event.getChannel().sendMessage("**Skipping song** :musical_note:").queue();
                musicManager.getGuildAudioManager(event.getGuild()).getTrackScheduler().nextTrack();
            }
        }
    }
}
