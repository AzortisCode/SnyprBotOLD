package com.azortis.snyprbot.settings;

import com.azortis.snyprbot.SnyprBot;
import com.azortis.snyprbot.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GuildSettings {

    private Long guildId;
    private boolean djOnly;
    private Long djRoleId;
    private Long musicTextChannelId;
    private Long musicVoiceChannelId;

    public GuildSettings(Long guildId, boolean djOnly, Long djRoleId, Long musicTextChannelId, Long musicVoiceChannelId) {
        this.guildId = guildId;
        this.djOnly = djOnly;
        this.djRoleId = djRoleId;
        this.musicTextChannelId = musicTextChannelId;
        this.musicVoiceChannelId = musicVoiceChannelId;
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setDjOnly(boolean djOnly) {
        this.djOnly = djOnly;
    }

    public boolean isDjOnly(){
        return djOnly;
    }

    public Long getDjRoleId() {
        return djRoleId;
    }

    public void setDjRoleId(Long djRoleId) {
        this.djRoleId = djRoleId;
    }

    public void setMusicTextChannelId(Long musicTextChannelId) {
        this.musicTextChannelId = musicTextChannelId;
    }

    public Long getMusicTextChannelId() {
        return musicTextChannelId;
    }

    public void setMusicVoiceChannelId(Long musicVoiceChannelId) {
        this.musicVoiceChannelId = musicVoiceChannelId;
    }

    public Long getMusicVoiceChannelId() {
        return musicVoiceChannelId;
    }

    public void save(){
        GuildSettings settings = this;
        Database database = SnyprBot.getDatabaseManager().getDatabase();
        new Thread(()-> {
            try(Connection connection = database.getConnection()){
                PreparedStatement statement = connection.prepareStatement("UPDATE guildSettings SET djOnly=?, djRoleId=?, musicTextChannelId=?, musicVoiceChannelId=? WHERE guildId=?");
                statement.setBoolean(1, settings.isDjOnly());
                statement.setLong(2, settings.getDjRoleId());
                statement.setLong(3, settings.getMusicTextChannelId());
                statement.setLong(4, settings.getMusicTextChannelId());
                statement.setLong(5, settings.getGuildId());

                statement.execute();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }).run();
    }
}
