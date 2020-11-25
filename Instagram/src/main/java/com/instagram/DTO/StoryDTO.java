package com.instagram.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.math.BigInteger;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class StoryDTO {


    private UUID storyId;
 /*   private float top;
    private float left;
    private float scale;
    private float rotation;
    private BigInteger colour;

  */
    private String storyMediaPath;
    private boolean isViewed;
    private UserShortDetailsDTO userShortDetailsDTO;

    public StoryDTO(UUID storyId/*, float top, float left, float scale, float rotation, BigInteger colour,*/, String storyMediaPath, boolean isViewed, UserShortDetailsDTO userShortDetailsDTO) {
        this.storyId = storyId;
       /* this.top = top;
        this.left = left;
        this.scale = scale;
        this.rotation = rotation;
        this.colour = colour;

        */
        this.storyMediaPath = storyMediaPath;
        this.isViewed = isViewed;
        this.userShortDetailsDTO = userShortDetailsDTO;
    }
}
