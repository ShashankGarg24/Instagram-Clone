package com.instagram.repository;

import com.instagram.models.FollowRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FollowRequestRepo extends JpaRepository<FollowRequest, UUID> {

    FollowRequest findByRequestToAndRequestFrom(UUID fromId, UUID toId);
    List<FollowRequest> findAllByRequestTo(UUID profileId);
}
