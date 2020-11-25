package com.instagram.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.instagram.models.Posts;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CurrentUserProfileDTO {

    private UUID profileId;
    private String username;
    private String fullName;
    private String userBio;
    private String userPrivacy;
    private boolean enabled;
    private String profilePicPath;
    private String birthDate;
    private int postNumber = 0;
    // private int storyNumber = 0;
    private int categoryNumber = 0;
    private int followersNumber = 0;
    private int followingNumber = 0;
    private List<PostDTO> posts = new ArrayList<PostDTO>();
    private List<PostDTO> taggedPosts = new ArrayList<>();

    public CurrentUserProfileDTO(UUID profileId, String username, String fullName, String userBio, String userPrivacy, boolean enabled, String profilePicPath, String birthDate, int postNumber, int categoryNumber, int followersNumber, int followingNumber, List<PostDTO> posts, List<PostDTO> taggedPosts) {
        this.profileId = profileId;
        this.username = username;
        this.fullName = fullName;
        this.userBio = userBio;
        this.userPrivacy = userPrivacy;
        this.enabled = enabled;
        this.profilePicPath = profilePicPath;
        this.birthDate = birthDate;
        this.postNumber = postNumber;
        this.categoryNumber = categoryNumber;
        this.followersNumber = followersNumber;
        this.followingNumber = followingNumber;
        this.posts = posts;
        this.taggedPosts = taggedPosts;
    }
}
