package com.instagram.controllers;


import com.instagram.serviceImpl.PostServiceImpl;
import com.instagram.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.mail.Multipart;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class Posts {

    @Autowired
    PostServiceImpl postService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ResourceLoader resourceLoader;
    @RequestMapping(method = RequestMethod.POST, path = "/uploadPost")
    public ResponseEntity<?> uploadPost(@RequestHeader("Authorization") String token, @RequestParam List<MultipartFile> media, @RequestParam String location, @RequestParam String caption, @RequestParam String taggedUsernames) throws Exception {

        System.out.println(taggedUsernames);
        return postService.uploadPost(token.substring(7), media, location, caption, taggedUsernames);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/updatePost")
    public ResponseEntity<?> updatePost(@RequestBody Map<String, String> request) {
        return postService.updatePost(request.get("token"), request.get("postId"), request.get("caption"));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/viewPost/{postId}")
    public ResponseEntity<?> viewPost(@RequestHeader(value = "Authorization") String token, @PathVariable("postId") String postId) {
        return postService.viewPost(token.substring(7), postId);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/pinPost")
    public ResponseEntity<?> pinPost(@RequestBody Map<String, String> request) {
        return postService.pinPost(request.get("token"), request.get("postId"));
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/unpinPost")
    public ResponseEntity<?> unpinPost(@RequestBody Map<String, String> request) {
        return postService.unpinPost(request.get("token"), request.get("postId"));

    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/changeCommentActivity")
    public ResponseEntity<?> changeCommentActivity(@RequestBody Map<String, String> request) {
        return postService.changeCommentActivity(request.get("token"), request.get("postId"));

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deletePost")
    public ResponseEntity<?> deletePost(@RequestBody Map<String, String> request) {
        return postService.deletePost(request.get("token"), request.get("postId"));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/createNewCategory")
    public ResponseEntity<?> createNewCategory(@RequestBody Map<String, String> request) {
        return postService.createNewCategory(request.get("name"), request.get("postId"), request.get("token"));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addToCategory")
    public ResponseEntity<?> addToCategory(@RequestBody Map<String, String> request) {
        return postService.addToCategory(request.get("postId"), request.get("categoryId"), request.get("token"));

    }

    @RequestMapping(method = RequestMethod.POST, path = "/removeFromCategory")
    public ResponseEntity<?> removeFromCategory(@RequestBody Map<String, String> request) {
        return postService.removeFromCategory(request.get("postId"), request.get("categoryId"), request.get("token"));

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteCategory")
    public ResponseEntity<?> deleteCategory(@RequestBody Map<String, String> request) {
        return postService.deleteCategory(request.get("categoryId"), request.get("token"));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categories")
    ResponseEntity<?> getUserCategories(@RequestHeader("Authorization") String token){
        return postService.getUserCategories(token.substring(7));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/savedPosts/{categoryId}")
    ResponseEntity<?> getSavedPostsFromCategories(@RequestHeader("Authorization") String token, @PathVariable("categoryId") String categoryId){
        return postService.getSavedPostsFromCategories(token.substring(7), categoryId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/likeDislikePost")
    public ResponseEntity<?> likeDislikePost(@RequestBody Map<String, String> request) {
        return postService.likeDislikePost(request.get("postId"), request.get("token"), Boolean.parseBoolean(request.get("likeStatus")));

    }

    @RequestMapping(method = RequestMethod.POST, path = "/addComment")
    public ResponseEntity<?> addComment(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> request) {
        return postService.addComment(request.get("postId"), token.substring(7), request.get("comment"));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getComments")
    public ResponseEntity<?> getCommentsByPostId(@RequestBody Map<String, String> request) {
        return postService.getCommentsByPostId(request.get("postId"));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteComment")
    public ResponseEntity<?> deleteComment(@RequestBody Map<String, String> request) {
        return postService.deleteComment(request.get("postId"), request.get("commentId"), request.get("token"));
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/likeDislikeComment")
    public ResponseEntity<?> likeDislikeComment(@RequestBody Map<String, String> request) {
        return postService.likeDislikeComment(request.get("commentId"), request.get("token"), Boolean.parseBoolean(request.get("likeStatus")));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addReply")
    public ResponseEntity<?> addReply(@RequestBody Map<String, String> request) {
        return postService.addReply(request.get("commentId"), request.get("token"), request.get("reply"));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getReplies")
    public ResponseEntity<?> getRepliesByCommentId(@RequestBody Map<String, String> request) {
        return postService.getRepliesByCommentId(request.get("commentId"));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteReply")
    public ResponseEntity<?> deleteReply(@RequestBody Map<String, String> request) {
        return postService.deleteReply(request.get("commentId"), request.get("replyId"), request.get("token"));
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/likeDislikeReply")
    public ResponseEntity<?> likeDislikeReply(@RequestBody Map<String, String> request) {
        return postService.likeDislikeReply(request.get("replyId"), request.get("token"), Boolean.parseBoolean(request.get("likeStatus")));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/uploadStory")
    public ResponseEntity<?> uploadStory(@RequestHeader("Authorization") String token,/* @RequestParam float top, @RequestParam float left, @RequestParam float scale, @RequestParam float rotation, @RequestParam BigInteger color, */@RequestParam MultipartFile media) throws Exception {

        return postService.uploadStory(token.substring(7), /*top, left, scale, rotation, color, */media);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteStory")
    public ResponseEntity<?> deleteStory(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> request) throws Exception {

        return postService.deleteStory(token.substring(7), request.get("storyId"));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/viewStory/{storyId}")
    public ResponseEntity<?> viewStory(@RequestHeader("Authorization") String token, @PathVariable("storyId") String storyId) throws Exception {

        return postService.viewStory(token.substring(7), storyId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getStories")
    public ResponseEntity<?> getStoriesFromFollowings(@RequestHeader("Authorization") String token) throws Exception {

        return postService.getStoriesFromFollowings(token.substring(7));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/feed")
    public ResponseEntity<?> getPostsFromFollowing(@RequestHeader("Authorization") String token) {

        return postService.getPostsFromFollowing(token.substring(7));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/explore")
    public ResponseEntity<?> getAllPostsByLikes(@RequestHeader("Authorization") String token) {

        return postService.getAllPostsByLikes(token.substring(7));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response, @PathVariable("name") String imageNAme) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        //var v = new ClassPathResource("\\images\\" + imageNAme);
        FileInputStream in = new FileInputStream("S:\\instagram\\Instagram\\Instagram\\src\\main\\resources\\images\\" + imageNAme);
         response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        //Path p = Paths.get("\\images\\" + imageNAme);
       // System.out.println(Files.probeContentType(p));
       StreamUtils.copy(in, response.getOutputStream());

    }

   /* @RequestMapping(method = RequestMethod.GET, path = "/images/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImagee(HttpServletResponse response, @PathVariable("name") String imageNAme) throws IOException {
        var v = new ClassPathResource("\\images\\" + imageNAme);
        Path p = Paths.get("\\images\\" + imageNAme);
        System.out.println(Files.probeContentType(p));

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(v.getInputStream()));
    }

    */
}
