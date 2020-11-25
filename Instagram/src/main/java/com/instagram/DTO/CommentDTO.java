package com.instagram.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CommentDTO {

    private UUID commentId;
    private String comment;
    private UserShortDetailsDTO userShortDetailsDTO;

    public CommentDTO(UUID commentId, String comment, UserShortDetailsDTO userShortDetailsDTO) {
        this.commentId = commentId;
        this.comment = comment;
        this.userShortDetailsDTO = userShortDetailsDTO;
    }
}
