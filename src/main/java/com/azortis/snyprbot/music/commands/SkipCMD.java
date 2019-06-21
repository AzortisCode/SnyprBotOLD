package com.azortis.snyprbot.music.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.CommandCategory;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.music.MusicManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SkipCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if(event.getGuild() != null){
            Guild guild = event.getGuild();
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
