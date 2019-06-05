package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.SnyprBot;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Arrays;

public class PlayCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.getGuild() != null) {
            VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
            String trackId;
            if(UrlValidator.getInstance().isValid(args[0])){
                trackId = args[0];
            }else{
                trackId = "ytsearch: " + Arrays.toString(args);
            }
            SnyprBot.getMusicManager().loadAndPlay(voiceChannel, event.getTextChannel(), trackId);
        }
    }
}
