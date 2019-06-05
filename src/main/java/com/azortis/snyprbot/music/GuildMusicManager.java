package com.azortis.snyprbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {
    private AudioPlayer player;
    private TrackScheduler trackScheduler;

    public GuildMusicManager(AudioPlayerManager playerManager){
        this.player = playerManager.createPlayer();
        this.trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public TrackScheduler getTrackScheduler() {
        return trackScheduler;
    }

    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }

}
