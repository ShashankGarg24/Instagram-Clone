package com.instagram.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Story {

    @Id
    private UUID storyId;
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime storyCreationTimeStamp;
    /*private float top;
    private float left;
    private float scale;
    private float rotation;
    private BigInteger colour;
    */

    private String storyMediaPath;

    @ElementCollection
    @JsonIgnore
    private List<UUID> viewerIds = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserProfile profile;

    public Story() {
    }

    public Story(/*float top, float left, float scale, float rotation, BigInteger colour,*/ UserProfile profile) {
        this.storyId = UUID.randomUUID();
        this.storyCreationTimeStamp = LocalDateTime.now();
        /*this.top = top;
        this.left = left;
        this.scale = scale;
        this.rotation = rotation;
        this.colour = colour;*/
        this.profile = profile;
    }

    public UUID getStoryId() {
        return storyId;
    }

    public void setStoryId(UUID storyId) {
        this.storyId = storyId;
    }

    public LocalDateTime getStoryCreationTimeStamp() {
        return storyCreationTimeStamp;
    }

    public void setStoryCreationTimeStamp(LocalDateTime storyCreationTimeStamp) {
        this.storyCreationTimeStamp = storyCreationTimeStamp;
    }
/*
    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public BigInteger getColour() {
        return colour;
    }

    public void setColour(BigInteger colour) {
        this.colour = colour;
    }
*/
    public String getStoryMediaPath() {
        return storyMediaPath;
    }

    public void setStoryMediaPath(String storyMediaPath) {
        this.storyMediaPath = storyMediaPath;
    }

    public List<UUID> getViewerIds() {
        return viewerIds;
    }

    public void addToViewerIds(UUID viewerId) {
        this.viewerIds.add(viewerId);
    }

    public void RemoveFromViewerIds(UUID viewerId) {
        this.viewerIds.remove(viewerId);
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }
}
