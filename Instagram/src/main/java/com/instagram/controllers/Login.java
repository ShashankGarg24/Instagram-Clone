package com.instagram.controllers;

import com.instagram.Configuration.JwtUtil;
import com.instagram.DTO.CurrentUserProfileDTO;
import com.instagram.DTO.PostDTO;
import com.instagram.DTO.ProfileDTO;
import com.instagram.models.*;
import com.instagram.models.Posts;
import com.instagram.repository.PostRepository;
import com.instagram.repository.ProfileRepository;
import com.instagram.repository.UserCredentialsRepo;
import com.instagram.serviceImpl.UserServiceImpl;
import com.instagram.services.PostService;
import com.instagram.services.UserService;
import com.instagram.services.VerificationMail;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api")
public class Login {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserCredentialsRepo userCredentialsRepo;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private VerificationMail mailService;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService1;


    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try {
            UserCredentials user;
            if (request.getUsername().contains("@")) {
                user = userService.findUserByEmail(request.getUsername());
                if(user == null){
                    return new ResponseEntity<>("email doesn't exist", HttpStatus.valueOf(404));
                }
                checkAccountAndPassword(user, request);
                System.out.println(user.isVerified());

                if (!user.isVerified()) {
                    mailService.sendVerificationEmail(user);
                    return new ResponseEntity<String>("User not verified. Please check EMAIL to verify.",
                            HttpStatus.NOT_ACCEPTABLE);
                }

                List<UserProfile> profiles = profileRepository.findAllByUserUserId(user.getUserId());

                if(profiles.isEmpty()){
                    return new ResponseEntity<>("No profiles created yet", HttpStatus.valueOf(300));
                }
                return new ResponseEntity<>(profiles, HttpStatus.OK);

            } else {
                UserProfile profile = profileRepository.findByUsername(request.getUsername());
                if(profile == null){
                    return new ResponseEntity<>("username not available", HttpStatus.valueOf(404));
                }
                user = profile.getUser();
                checkAccountAndPassword(user, request);
                return createLoginToken(request.getUsername(),request.getPassword());
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    private void checkAccountAndPassword(UserCredentials user, LoginRequest request) throws Exception {

        if (user == null) {

            ResponseEntity.status(404);
            throw new Exception("Username/Email doesn't exist!");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean check = encoder.matches(request.getPassword(), user.getUserPassword());

        if (!check) {
            ResponseEntity.status(400);
            throw new Exception("Password is incorrect");        }

        return;
    }


    public ResponseEntity<?> createLoginToken(String username, String password) {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(username);

            if (userDetails.isEnabled()) {
                final String accessToken = jwtTokenUtil.generateToken(userDetails.getUsername(), password);
                final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails.getUsername(), password);

                return new ResponseEntity<LoginResponse>(new LoginResponse(accessToken, refreshToken, convertToCurrentProfileDTO(profileRepository.findByUsername(username))), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("User is disabled by ADMIN.", HttpStatus.BAD_REQUEST);
            }
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(400));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("bad credential", HttpStatus.valueOf(410));//improvisation required
        }
    }


 /*   @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {

        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
        final String jwt = jwtTokenUtil.generateRefreshToken(claims);
        User user = userService.findUserByUsername(jwtTokenUtil.getUsernameFromToken(jwt));
        System.out.println("Token refreshed");
        return new ResponseEntity<LoginResponse>(new LoginResponse(jwt, user), HttpStatus.OK);

    }
*/

    public CurrentUserProfileDTO convertToCurrentProfileDTO(UserProfile profile){


        List<Posts> posts = postRepository.findAllByProfileProfileId(profile.getProfileId());
        Comparator<Posts> compareByDateOfCreation = Comparator.comparing(Posts::getpostCreationTimeStamp);
        Collections.sort(posts, compareByDateOfCreation.reversed());
        List<PostDTO> postDTOS = new ArrayList<>();
        List<PostDTO> taggedPostDTOs = new ArrayList<>();
        for(Posts post : posts){
            if(post.isPinned()){
                postDTOS.add(0,postService.convertToPostDTO(post, postService.itemIsLiked(post.getPostId(), profile.getProfileId()), postService.postIsSaved(post, profile)));
                continue;
            }
            postDTOS.add(postService.convertToPostDTO(post, postService.itemIsLiked(post.getPostId(), profile.getProfileId()), postService.postIsSaved(post, profile)));
        }
        for(Posts post : profile.getTaggedPosts()){
            taggedPostDTOs.add(postService.convertToPostDTO(post, postService.itemIsLiked(post.getPostId(), profile.getProfileId()), postService.postIsSaved(post, profile)));
        }
        return new CurrentUserProfileDTO(profile.getProfileId(), profile.getUsername(), profile.getFullName(), profile.getUserBio(), profile.getUserPrivacy(), profile.isEnabled(), profile.getProfilePicPath(), profile.getBirthDate(), profile.getPostNumber(), profile.getCategoryNumber(), profile.getFollowersNumber(), profile.getFollowingNumber(), postDTOS, taggedPostDTOs);
    }


}
