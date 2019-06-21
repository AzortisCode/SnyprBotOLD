package com.azortis.snyprbot.settings;

public class GuildSettings {

    private boolean djOnly;
    private Long djRoleId;
    private Long musicTextChannelId;
    private Long musicVoiceChannelId;

    public GuildSettings(boolean djOnly, Long djRoleId, Long musicTextChannelId, Long musicVoiceChannelId) {
        this.djOnly = djOnly;
        this.djRoleId = djRoleId;
        this.musicTextChannelId = musicTextChannelId;
        this.musicVoiceChannelId = musicVoiceChannelId;
    }

    public Long getDjRoleId() {
        return djRoleId;
    }

    public Long getMusicTextChannelId() {
        return musicTextChannelId;
    }

    public Long getMusicVoiceChannelId() {
        return musicVoiceChannelId;
    }
}
