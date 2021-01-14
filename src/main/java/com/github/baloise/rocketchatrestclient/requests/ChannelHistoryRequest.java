package com.github.baloise.rocketchatrestclient.requests;

import java.util.Date;

import com.github.baloise.rocketchatrestclient.util.Misc;

public class ChannelHistoryRequest {
    private String roomId, latest, oldest;
    private boolean inclusive;

    public ChannelHistoryRequest(String roomId) {
        this.roomId = roomId;
    }

    public ChannelHistoryRequest(String roomId, String latest, String oldest, boolean inclusive) {
        this.roomId = roomId;
        this.latest = latest;
        this.oldest = oldest;
        this.inclusive = inclusive;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getLatest() {
        return this.latest;
    }

    public void setLatest(Date latest) {
        this.latest = Misc.dateFormat.format(latest);
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getOldest() {
        return this.oldest;
    }

    public void setOldest(Date oldest) {
        this.oldest = Misc.dateFormat.format(oldest);
    }

    public void setOldest(String oldest) {
        this.oldest = oldest;
    }

    public boolean getInclusive() {
        return this.inclusive;
    }

    public void setInclusive(boolean inclusive) {
        this.inclusive = inclusive;
    }
}
