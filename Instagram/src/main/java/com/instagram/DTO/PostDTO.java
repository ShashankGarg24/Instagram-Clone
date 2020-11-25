package com.instagram.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.instagram.models.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PostDTO {


    private UUID postId;
    private Integer likes;
    private String location;
    private String caption;
    private boolean commentActivity;
    private boolean pinned;
    private String profilePicPathOfUploader;
    private String fullNameOfUploader;
    private String usernameOfUploader;
    private Media postMedia;
    private List<CommentDTO> comments;
    private boolean isLiked;
    private boolean isSaved;

    public PostDTO(UUID postId, Integer likes, String location, String caption, boolean commentActivity, boolean pinned, String profilePicPathOfUploader, String fullNameOfUploader, String usernameOfUploader, Media postMedia, List<CommentDTO> comments, boolean isLiked, boolean isSaved) {
        this.postId = postId;
        this.likes = likes;
        this.location = location;
        this.caption = caption;
        this.commentActivity = commentActivity;
        this.pinned = pinned;
        this.profilePicPathOfUploader = profilePicPathOfUploader;
        this.fullNameOfUploader = fullNameOfUploader;
        this.usernameOfUploader = usernameOfUploader;
        this.postMedia = postMedia;
        this.comments = comments;
        this.isLiked = isLiked;
        this.isSaved = isSaved;
    }
}
