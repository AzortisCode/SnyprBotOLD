package com.azortis.snyprbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.HashMap;
import java.util.Map;

public class MusicManager {
    private final AudioPlayerManager playerManager;
    private final YoutubeSearchProvider youtubeSearchProvider;
    private final Map<Long, GuildMusicManager> musicManagers;

    public MusicManager(){
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        youtubeSearchProvider = new YoutubeSearchProvider(playerManager.source(YoutubeAudioSourceManager.class));
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public GuildMusicManager getGuildAudioManager(Guild guild){
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);
        if(musicManager == null){
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }

    public void queue(VoiceChannel voiceChannel, TextChannel textChannel, String trackId, long requesterId){
        GuildMusicManager musicManager = getGuildAudioManager(voiceChannel.getGuild());
        playerManager.loadItemOrdered(musicManager, trackId, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                audioTrack.setUserData(requesterId);
                queue(voiceChannel, textChannel, musicManager, audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                audioPlaylist.getTracks().forEach(track -> track.setUserData(requesterId));
                queueList(voiceChannel, textChannel, musicManager, audioPlaylist);
            }

            @Override
            public void noMatches() {
                textChannel.sendMessage(":x: **No matches found!**").queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                textChannel.sendMessage(":x: **Oops something went wrong, try again!**").queue();
            }
        });
    }


    private void queue(VoiceChannel voiceChannel, TextChannel textChannel, GuildMusicManager musicManager, AudioTrack track){
        openAudioConnection(voiceChannel);
        musicManager.getTrackScheduler().queue(track, textChannel);
    }

    private void queueList(VoiceChannel voiceChannel, TextChannel textChannel, GuildMusicManager musicManager, AudioPlaylist playlist){
        openAudioConnection(voiceChannel);
        musicManager.getTrackScheduler().queueList(playlist, textChannel);
    }

    private void openAudioConnection(VoiceChannel channel){
        if(!channel.getGuild().getAudioManager().isConnected() && !channel.getGuild().getAudioManager().isAttemptingToConnect()){
            channel.getGuild().getAudioManager().openAudioConnection(channel);
        }
    }

    public void closeAudioConnection(Guild guild){
        if(guild.getAudioManager().isConnected()){
            guild.getAudioManager().closeAudioConnection();
        }
    }

    public YoutubeSearchProvider getYoutubeSearchProvider() {
        return youtubeSearchProvider;
    }
}
