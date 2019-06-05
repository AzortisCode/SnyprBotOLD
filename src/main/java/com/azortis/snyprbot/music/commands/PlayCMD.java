package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.SnyprBot;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PlayCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.getGuild() != null) {
            VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
            SnyprBot.getAudioManager().loadAndPlay(voiceChannel, event.getTextChannel(), args[0]);
        }
    }
}
