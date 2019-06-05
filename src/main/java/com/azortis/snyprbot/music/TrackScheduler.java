package com.azortis.snyprbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.entities.TextChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private TextChannel channel; //Stored so messages will be sent to the channel where the first command got called in

    private boolean repeat = false;

    TrackScheduler(AudioPlayer player){
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    void queue(AudioTrack track, TextChannel channel){
        if(this.channel == null)this.channel = channel;
        if(!player.startTrack(track, true)){
            queue.offer(track);
            channel.sendMessage("**Added** `" + track.getInfo().title + "` **to the queue!**").queue();
        }else{
            channel.sendMessage(":musical_note: **Now playing** `" + track.getInfo().title + "`").queue();
        }
    }

    void queueList(AudioPlaylist playlist, TextChannel channel){
        if(this.channel == null)this.channel = channel;
        for (AudioTrack track : playlist.getTracks()){
            queue.offer(track);
        }
        channel.sendMessage("**Added playlist to the queue!**").queue();
    }

    public void emptyQueue(){
        queue.clear();
    }

    public void unbindTextChannel(){
        this.channel = null;
    }

    public void nextTrack(){
        AudioTrack track = queue.poll();
        player.startTrack(track, false);
        if(track != null)channel.sendMessage(":musical_note: **Now playing** `" + track.getInfo().title + "`").queue();
    }

    public boolean isRepeat(){
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(endReason.mayStartNext){
            if(repeat){
                player.startTrack(track.makeClone(), false);
                channel.sendMessage(":musical_note: **Now playing** `" + track.makeClone().getInfo().title + "`").queue();
            }else {
                nextTrack();
            }
        }
    }

}
