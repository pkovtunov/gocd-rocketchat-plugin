package com.github.baloise.rocketchatrestclient.model;

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Integration {
    private IntegrationType type;
    private String name, username, channel, triggerWords, alias, avatar, emoji, token, script;
    private ArrayList<String> urls;
    private boolean enabled, scriptEnabled;

    public Integration(IntegrationType type) {
        this.urls = new ArrayList<String>();
        this.type = type;
    }

    public Integration(IntegrationType type, String name) {
        this.urls = new ArrayList<String>();
        this.type = type;
        this.name = name;
    }

    public void setType(IntegrationType type) {
        this.type = type;
    }

    public IntegrationType getType() {
        return this.type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonGetter("enabled")
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUrls(String[] urls) {
        this.urls = new ArrayList<String>(Arrays.asList(urls));
    }

    public String[] getUrls() {
        return this.urls.toArray(new String[this.urls.size()]);
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setScriptEnabled(boolean scriptEnabled) {
        this.scriptEnabled = scriptEnabled;
    }

    @JsonGetter("scriptEnabled")
    public boolean isScriptEnabled() {
        return this.scriptEnabled;
    }

    /**
     * Sets the words which trigger this Integration, is a list separated by
     * commas.
     *
     * @param triggerWords
     *            the comma separated words
     */
    public void setTriggerWords(String triggerWords) {
        this.triggerWords = triggerWords;
    }

    public String getTriggerWords() {
        return this.triggerWords;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return this.emoji;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScript() {
        return this.script;
    }
}
