package com.instagram.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BirthdayDTO {

    private List<UserShortDetailsDTO> today;
    private List<UserShortDetailsDTO> upcoming;
    private List<UserShortDetailsDTO> passed;

    public BirthdayDTO(List<UserShortDetailsDTO> today, List<UserShortDetailsDTO> upcoming, List<UserShortDetailsDTO> passed) {
        this.today = today;
        this.upcoming = upcoming;
        this.passed = passed;
    }
}
