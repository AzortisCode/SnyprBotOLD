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
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < args.length; i++){
                    stringBuilder.append(args[i]).append(" ");
                }
                String searchArgs = stringBuilder.toString().trim();
                event.getChannel().sendMessage("**Searching** :mag_right: `" + searchArgs + "`").queue();
                AudioPlaylist playlist = (AudioPlaylist) musicManager.getYoutubeSearchProvider().loadSearchResult(searchArgs);
                trackId = playlist.getTracks().get(0).getInfo().uri;
            }
            musicManager.queue(voiceChannel, event.getTextChannel(), trackId, event.getAuthor().getIdLong());
        }
    }
}
