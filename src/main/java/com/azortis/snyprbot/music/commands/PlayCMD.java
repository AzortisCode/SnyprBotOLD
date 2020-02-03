package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.CommandCategory;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.music.MusicManager;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.validator.routines.UrlValidator;

public class PlayCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.isFromGuild()) {
            MusicManager musicManager = SnyprBot.getMusicManager();
            VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
            if(voiceChannel == null){
                event.getMessage().getChannel().sendMessage(":x: **You must be in a voice channel to do that!**").queue();
                return;
            }
            String trackId;
            if(UrlValidator.getInstance().isValid(args[0])){
                trackId = args[0];
            }else{
                StringBuilder stringBuilder = new StringBuilder();
                for (String arg : args){
                    stringBuilder.append(arg).append(" ");
                }
                String searchArgs = stringBuilder.toString().trim();
                event.getChannel().sendMessage("**Searching** :mag_right: `" + searchArgs + "`").queue();
                AudioPlaylist playlist = (AudioPlaylist) musicManager.getYoutubeSearchProvider().loadSearchResult(searchArgs, musicManager.getYoutubeSearchProvider());
                trackId = playlist.getTracks().get(0).getInfo().uri;
            }
            musicManager.queue(voiceChannel, event.getTextChannel(), trackId, event.getAuthor().getIdLong());
        }
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

}
