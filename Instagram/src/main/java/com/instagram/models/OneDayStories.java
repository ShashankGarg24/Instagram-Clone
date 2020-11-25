package com.instagram.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class OneDayStories {

    @Id
    private UUID storyId;
    private UUID profileId;
    private LocalDateTime storyTime;

    public OneDayStories() {
    }

    public OneDayStories(UUID storyId, UUID profileId, LocalDateTime storyTime) {
        this.storyId = storyId;
        this.profileId = profileId;
        this.storyTime = storyTime;
    }

    public UUID getStoryId() {
        return storyId;
    }

    public void setStoryId(UUID storyId) {
        this.storyId = storyId;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public LocalDateTime getStoryTime() {
        return storyTime;
    }

    public void setStoryTime(LocalDateTime storyTime) {
        this.storyTime = storyTime;
    }
}
