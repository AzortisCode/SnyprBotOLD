package com.azortis.snyprbot.commands;

import com.azortis.snyprbot.Command;
import com.azortis.snyprbot.CommandCategory;
import com.azortis.snyprbot.SnyprBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;

public class PingCMD implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();
        MessageEmbed pingEmbed = new EmbedBuilder()
                .setTitle("Pong! :ping_pong:")
                .setColor(Color.decode(SnyprBot.getConfig().getEmbedColor()))
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .addField("Calculated ping", "`" + Math.round(event.getJDA().getPing()) + "ms` Latency", false)
                .setFooter("Executed by: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl())
                .setTimestamp(Instant.now()).build();
        channel.sendMessage(pingEmbed).queue();
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.INFO;
    }
}
