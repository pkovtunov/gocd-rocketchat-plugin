package com.github.baloise.rocketchatrestclient.model;

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Attachment {
    private String color, text, ts, thumbUrl, messageLink;
    private boolean collapsed;
    private String authorName, authorLink, authorIcon;
    private String title, titleLink, titleLinkDownload;
    private String imageUrl, audioUrl, videoUrl;
    private ArrayList<AttachmentField> fields;

    public Attachment() {
        this.fields = new ArrayList<AttachmentField>();
    }

    public Attachment(String text) {
        this.fields = new ArrayList<AttachmentField>();
        this.text = text;
    }

    public Attachment(String title, String text) {
        this.fields = new ArrayList<AttachmentField>();
        this.title = title;
        this.text = text;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    @JsonGetter("collapsed")
    public boolean isCollapsed() {
        return this.collapsed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitleLink(String titleLink) {
        this.titleLink = titleLink;
    }

    public String getTitleLink() {
        return this.titleLink;
    }

    public void setTitleLinkDownload(String titleLinkDownload) {
        this.titleLinkDownload = titleLinkDownload;
    }

    public String getTitleLinkDownload() {
        return this.titleLinkDownload;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonSetter("ts")
    public void setTimestamp(String timestamp) {
        this.ts = timestamp;
    }

    @JsonGetter("ts")
    public String getTimestamp() {
        return this.ts;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getThumbUrl() {
        return this.thumbUrl;
    }

    public void setMessageLink(String messageLink) {
        this.messageLink = messageLink;
    }

    public String getMessageLink() {
        return this.messageLink;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorLink(String authorLink) {
        this.authorLink = authorLink;
    }

    public String getAuthorLink() {
        return this.authorLink;
    }

    public void setAuthorIcon(String authorIcon) {
        this.authorIcon = authorIcon;
    }

    public String getAuthorIcon() {
        return this.authorIcon;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getAudioUrl() {
        return this.audioUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setFields(AttachmentField[] fields) {
        this.fields = new ArrayList<AttachmentField>(Arrays.asList(fields));
    }

    public AttachmentField[] getFields() {
        return this.fields.toArray(new AttachmentField[this.fields.size()]);
    }
}
