package com.azortis.snyprbot;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;

public class EventListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event){
        MessageChannel botLog = SnyprBot.getClient().getTextChannelById(SnyprBot.getConfig().getBotLogChannelID());
        MessageEmbed readyEmbed = new EmbedBuilder()
                .setTitle("Bot waking up...")
                .setColor(Color.decode(SnyprBot.getConfig().getEmbedColor()))
                .setDescription("Bot running on `SnyprBotJAVA` version `v1.0.0`")
                .setFooter("© SnyprBot | https://snyperbot.xyz", null)
                .setTimestamp(Instant.now()).build();
        botLog.sendMessage(readyEmbed).queue();
        event.getJDA().getPresence().setGame(Game.listening(SnyprBot.getConfig().getDefaultActivity()));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(!event.getAuthor().isBot()){
            String content = event.getMessage().getContentRaw();
            if(content.startsWith(SnyprBot.getConfig().getDefaultPrefix())){
                String[] contentArray = content.split(" ");
                String key = contentArray[0].replaceAll(SnyprBot.getConfig().getDefaultPrefix(), "");
                if (SnyprBot.getCommandMap().containsKey(key)) {
                    String[] args = content.replaceAll(contentArray[0], "").trim().split(" ");
                    SnyprBot.getCommandMap().get(key).execute(event, args);
                }
            }
        }
    }
}
