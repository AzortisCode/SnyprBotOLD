package com.azortis.snyprbot.settings;

import com.azortis.snyprbot.Callback;
import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.database.Database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
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

    public GuildSettings getGuildSettings(Long guildId, Callback callback){
        if(!guildSettingsCache.containsKey(guildId)){
            getGuildSettingsAsync(guildId, callback);
        }
        return guildSettingsCache.get(guildId);
    }

    private void getGuildSettingsAsync(Long guildId, Callback callback){
        Database database = SnyprBot.getDatabaseManager().getDatabase();
        new Thread(()-> {
            GuildSettings settings;
            try(Connection connection = database.getConnection()){
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM guildSettings WHERE guildId=?", ResultSet.CONCUR_READ_ONLY);
                statement.setLong(1, guildId);

                ResultSet result = statement.executeQuery();
                if(result != null){
                    settings = new GuildSettings(guildId, result.getBoolean("djOnly"), result.getLong("djRoleID"), result.getLong("musicTextChannelId"), result.getLong("musicVoiceChannelId"));
                    guildSettingsCache.put(guildId, settings);
                    callback.run(settings);
                }else{
                    settings = new GuildSettings(guildId,false, null, null, null);
                    guildSettingsCache.put(guildId, settings);
                    callback.run(settings);
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }).run();
    }

}
