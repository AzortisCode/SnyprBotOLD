package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.music.MusicManager;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Arrays;

public class PlayCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.getGuild() != null) {
            MusicManager musicManager = SnyprBot.getMusicManager();
            VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
            String trackId;
            if(UrlValidator.getInstance().isValid(args[0])){
                trackId = args[0];
            }else{
                String searchArgs = Arrays.toString(args);
                AudioPlaylist playlist = (AudioPlaylist) musicManager.getYoutubeSearchProvider().loadSearchResult(searchArgs);
                trackId = playlist.getTracks().get(0).getInfo().uri;
            }
            musicManager.queue(voiceChannel, event.getTextChannel(), trackId);
        }
    }
}
