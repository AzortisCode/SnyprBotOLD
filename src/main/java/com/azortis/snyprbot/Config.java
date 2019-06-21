package com.azortis.snyprbot;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class Config {

    private String token;
    private String defaultPrefix;
    private List<Long> ownerIDs;
    private long botLogChannelID;
    private String embedColor;
    private String defaultActivity;
    private Map<String, String> guildSettings;

    public Config(String token, String defaultPrefix, List<Long> ownerIds, long botLogChannelID, String embedColor, String defaultActivity, Map<String, String> guildSettings){
        this.token = token;
        this.defaultPrefix = defaultPrefix;
        this.ownerIDs = ownerIds;
        this.botLogChannelID = botLogChannelID;
        this.embedColor = embedColor;
        this.defaultActivity = defaultActivity;
        this.guildSettings = guildSettings;
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

    public String getDefaultActivity() {
        return defaultActivity;
    }

    public Map<String, String> getGuildSettings() {
        return guildSettings;
    }
}
