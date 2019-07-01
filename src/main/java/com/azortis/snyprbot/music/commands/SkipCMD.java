package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.CommandCategory;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@SuppressWarnings("all")
public class SkipCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.isFromGuild()){
            Guild guild = event.getGuild();
            if(guild.getMember(event.getJDA().getSelfUser()).getVoiceState().getChannel() == null){
                event.getChannel().sendMessage(":x: **I'm not in a voice channel!**").queue();
                return;
            }
            if(event.getMember().getVoiceState().getChannel() == null){
                event.getChannel().sendMessage(":x: **You must be in my voice channel to do that!**").queue();
                return;
            }
            if(event.getMember().getVoiceState().getChannel() == guild.getMember(event.getJDA().getSelfUser()).getVoiceState().getChannel()){
                MusicManager musicManager = SnyprBot.getMusicManager();
                event.getChannel().sendMessage(":fast_forward: **Skipped!**").queue();
                musicManager.getGuildAudioManager(event.getGuild()).getTrackScheduler().nextTrack();
            }
        }
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

}
