package com.azortis.snyprbot.music;

import com.azortis.snyprbot.SnyprBot;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private AudioTrack upNext;
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
            this.channel.sendMessage("**Added** `" + track.getInfo().title + "` **to the queue!**").queue();
        }else{
            User requester = SnyprBot.getClient().getUserById((Long) track.getUserData());
            long duration = track.getInfo().length;
            MessageEmbed playingNowEmbed = new EmbedBuilder()
                    .setTitle("Now playing :musical_note:")
                    .setColor(Color.decode(SnyprBot.getConfig().getEmbedColor()))
                    .setThumbnail("http://img.youtube.com/vi/" + track.getInfo().identifier + "/0.jpg")
                    .setDescription("[" + track.getInfo().title + "](" + track.getInfo().uri + ")")
                    .addField("Length:", "`" + String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(duration),
                            TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))) + "`", true)
                    .addField("Up next:", "`nothing`", true)
                    .setFooter("Requested by: " + requester.getName() + "#" + requester.getDiscriminator(), requester.getAvatarUrl())
                    .setTimestamp(Instant.now()).build();
            this.channel.sendMessage(playingNowEmbed).queue();
            this.channel.sendMessage(":musical_note: **Playing** `" + track.getInfo().title + "` **now!**").queue();
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
        upNext = null;
    }

    public void unbindTextChannel(){
        this.channel = null;
    }

    public void nextTrack(){
        AudioTrack track = upNext;
        if(track == null)track = queue.poll();
        player.startTrack(track, false);
        if(track != null){
            User requester = SnyprBot.getClient().getUserById((Long) track.getUserData());
            long duration = track.getInfo().length;
            upNext = queue.poll();
            String nextSongTitle;
            if(upNext != null){
                nextSongTitle = upNext.getInfo().title;
            }else{
                nextSongTitle = "nothing";
            }
            MessageEmbed playingNowEmbed = new EmbedBuilder()
                    .setTitle("Now playing :musical_note:")
                    .setColor(Color.decode(SnyprBot.getConfig().getEmbedColor()))
                    .setThumbnail("http://img.youtube.com/vi/" + track.getInfo().identifier + "/0.jpg")
                    .setDescription("[" + track.getInfo().title + "](" + track.getInfo().uri + ")")
                    .addField("Length:", "`" + String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(duration),
                            TimeUnit.MILLISECONDS.toSeconds(duration) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))) + "`", true)
                    .addField("Up next:", "`" + nextSongTitle + "`", true)
                    .setFooter("Requested by: " + requester.getName() + "#" + requester.getDiscriminator(), requester.getAvatarUrl())
                    .setTimestamp(Instant.now()).build();
            this.channel.sendMessage(playingNowEmbed).queue();
            this.channel.sendMessage(":musical_note: **Playing** `" + track.getInfo().title + "` **now!**").queue();
        }
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
                User requester = SnyprBot.getClient().getUserById((Long) track.getUserData());
                long duration = track.getInfo().length;
                MessageEmbed playingNowEmbed = new EmbedBuilder()
                        .setTitle("Now playing :musical_note:")
                        .setColor(Color.decode(SnyprBot.getConfig().getEmbedColor()))
                        .setThumbnail("http://img.youtube.com/vi/" + track.getInfo().identifier + "/0.jpg")
                        .setDescription("[" + track.getInfo().title + "](" + track.getInfo().uri + ")")
                        .addField("Length:", "`" + String.format("%02d:%02d",
                                TimeUnit.MILLISECONDS.toMinutes(duration),
                                TimeUnit.MILLISECONDS.toSeconds(duration) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))) + "`", true)
                        .addField("Up next:", "`" + track.getInfo().title + "`", true)
                        .setFooter("Requested by: " + requester.getName() + "#" + requester.getDiscriminator(), requester.getAvatarUrl())
                        .setTimestamp(Instant.now()).build();
                this.channel.sendMessage(playingNowEmbed).queue();
                this.channel.sendMessage(":musical_note: **Playing** `" + track.getInfo().title + "` **now!**").queue();
            }else {
                nextTrack();
            }
        }
    }

}
