package com.azortis.snyprbot;

import java.util.List;

@SuppressWarnings("all")
public class Config {

    private String token;
    private String defaultPrefix;
    private List<Long> ownerIDs;
    private long botLogChannelID;
    private String embedColor;

    public Config(String token, String defaultPrefix, List<Long> ownerIds, long botLogChannelID, String embedColor){
        this.token = token;
        this.defaultPrefix = defaultPrefix;
        this.ownerIDs = ownerIds;
        this.botLogChannelID = botLogChannelID;
        this.embedColor = embedColor;
    }

    public String getToken(){
        return token;
    }

    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    public List<Long> getOwnerIDs() {
        return ownerIDs;
    }

    public long getBotLogChannelID() {
        return botLogChannelID;
    }

    public String getEmbedColor() {
        return embedColor;
    }
}
