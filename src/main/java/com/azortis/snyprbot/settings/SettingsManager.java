package com.azortis.snyprbot.settings;

import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.database.Database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsManager {
    private Map<Long, GuildSettings> guildSettingsCache = new HashMap<>();

    public SettingsManager(){
        Database database = SnyprBot.getDatabaseManager().getDatabase();
        try(Connection connection = database.getConnection()){
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS guildSettings(guildId BIGINT, djOnly bool, djRoleID BIGINT, musicTextChannelId BIGINT, musicVoiceChannelId BIGINT)");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public GuildSettings getGuildSettings(Long guildId){
        if(!guildSettingsCache.containsKey(guildId)){
            Database database = SnyprBot.getDatabaseManager().getDatabase();
            GuildSettings settings;
            try(Connection connection = database.getConnection()){
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM guildSettings WHERE guildId=?", ResultSet.CONCUR_READ_ONLY);
                statement.setLong(1, guildId);

                ResultSet result = statement.executeQuery();
                if(result != null){
                    settings = new GuildSettings(result.getBoolean("djOnly"), result.getLong("djRoleID"), result.getLong("musicTextChannelId"), result.getLong("musicVoiceChannelId"));
                    guildSettingsCache.put(guildId, settings);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return guildSettingsCache.get(guildId);
    }

}
