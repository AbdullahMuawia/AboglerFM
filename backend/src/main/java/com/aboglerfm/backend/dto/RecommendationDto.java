
package com.aboglerfm.backend.dto;

import java.io.Serializable;
import lombok.Generated;

public class RecommendationDto implements Serializable {
    private String song;
    private String artist;
    private String reason;
    private static final long serialVersionUID = 1L;

    @Generated
    public String getSong() {
        return this.song;
    }

    @Generated
    public String getArtist() {
        return this.artist;
    }

    @Generated
    public String getReason() {
        return this.reason;
    }

    @Generated
    public void setSong(final String song) {
        this.song = song;
    }

    @Generated
    public void setArtist(final String artist) {
        this.artist = artist;
    }

    @Generated
    public void setReason(final String reason) {
        this.reason = reason;
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RecommendationDto)) {
            return false;
        } else {
            RecommendationDto other = (RecommendationDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$song = this.getSong();
                Object other$song = other.getSong();
                if (this$song == null) {
                    if (other$song != null) {
                        return false;
                    }
                } else if (!this$song.equals(other$song)) {
                    return false;
                }

                Object this$artist = this.getArtist();
                Object other$artist = other.getArtist();
                if (this$artist == null) {
                    if (other$artist != null) {
                        return false;
                    }
                } else if (!this$artist.equals(other$artist)) {
                    return false;
                }

                Object this$reason = this.getReason();
                Object other$reason = other.getReason();
                if (this$reason == null) {
                    if (other$reason != null) {
                        return false;
                    }
                } else if (!this$reason.equals(other$reason)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof RecommendationDto;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $song = this.getSong();
        result = result * 59 + ($song == null ? 43 : $song.hashCode());
        Object $artist = this.getArtist();
        result = result * 59 + ($artist == null ? 43 : $artist.hashCode());
        Object $reason = this.getReason();
        result = result * 59 + ($reason == null ? 43 : $reason.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        String var10000 = this.getSong();
        return "RecommendationDto(song=" + var10000 + ", artist=" + this.getArtist() + ", reason=" + this.getReason() + ")";
    }

    @Generated
    public RecommendationDto(final String song, final String artist, final String reason) {
        this.song = song;
        this.artist = artist;
        this.reason = reason;
    }

    @Generated
    public RecommendationDto() {
    }
}
