package com.instagram.serviceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface PostServiceImpl {

    ResponseEntity uploadPost(String token, List<MultipartFile> media, String location, String caption, String taggedUserIds) throws Exception;

    ResponseEntity<?> updatePost(String token, String postId, String caption);

    ResponseEntity<?> unpinPost(String token, String postId);

    ResponseEntity<?> pinPost(String token, String postId);

    ResponseEntity<?> deletePost(String token, String postId);

    ResponseEntity<?> viewPost(String token, String postId);

    ResponseEntity<?> createNewCategory(String name, String postId, String token);

    ResponseEntity<?> addToCategory(String postId, String categoryId, String token);

    ResponseEntity<?> removeFromCategory(String postId, String categoryId, String token);

    ResponseEntity<?> deleteCategory(String categoryId, String token);

    ResponseEntity<?> getUserCategories(String token);

    ResponseEntity<?> getSavedPostsFromCategories(String token, String categoryId);

    ResponseEntity<?> likeDislikePost(String postId, String token, boolean likeStatus);

    ResponseEntity<?> addComment(String postId, String token, String comment);

    ResponseEntity<?> getCommentsByPostId(String postId);

    ResponseEntity<?> deleteComment(String postId, String commentId, String token);

    ResponseEntity<?> likeDislikeComment(String commentId, String token, boolean likeStatus);

    ResponseEntity<?> addReply(String commentId, String token, String reply);

    ResponseEntity<?> getRepliesByCommentId(String commentId);

    ResponseEntity<?> deleteReply(String commentId, String replyId, String token);

    ResponseEntity<?> likeDislikeReply(String replyId, String token, boolean likeStatus);

    ResponseEntity<?> uploadStory(String token,/* float top, float left, float scale, float rotation, BigInteger color,*/ MultipartFile media);

    ResponseEntity<?> deleteStory(String token, String storyId);

    ResponseEntity<?> viewStory(String token, String  storyId);

    ResponseEntity<?> getStoriesFromFollowings(String token);

    ResponseEntity<?> getPostsFromFollowing(String token);

    ResponseEntity<?> getAllPostsByLikes(String token);

    ResponseEntity<?> changeCommentActivity(String token, String postId);
}