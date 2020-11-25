package com.instagram.repository;

import com.instagram.models.OneDayStories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OneDayStoriesRepo extends JpaRepository<OneDayStories, UUID> {

    OneDayStories findByStoryId(UUID storyId);
    List<OneDayStories> findAllByStoryTimeIsLessThanEqual(LocalDateTime currentDateTime);

}
