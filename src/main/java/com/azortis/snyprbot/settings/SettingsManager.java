package com.azortis.snyprbot.settings;

import net.dv8tion.jda.core.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class SettingsManager {
    private Map<Long, GuildSettings> settingsCache;

    public SettingsManager(){
        settingsCache = new HashMap<>();
    }

    public GuildSettings getSettings(Guild guild){
        return settingsCache.get(guild);
    }
}
