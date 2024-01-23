package com.smsoft.board.service.impl;

import com.smsoft.board.dto.PostDTO;
import com.smsoft.board.dto.UserDTO;
import com.smsoft.board.mapper.PostMapper;
import com.smsoft.board.mapper.UserProfileMapper;
import com.smsoft.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final UserProfileMapper userProfileMapper;
    private final PostMapper postMapper;

    @Override
    public void registerPosts(String userId, PostDTO postDTO) {
        UserDTO userProfile = userProfileMapper.getUserProfile(userId);

        if (userProfile == null) {
            log.error("register ERROR {} ", userId);
            throw new RuntimeException("register ERROR " + userId);
        }

        postDTO.setUserId(userProfile.getId());

        postMapper.registerPosts(postDTO);
    }

    @Override
    public List<PostDTO> getMyPosts(int userId) {
        List<PostDTO> myPosts = postMapper.getMyPosts(userId);
        return myPosts;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        if (postDTO == null || postDTO.getId()==0) {
            log.error("updatePosts ERROR {} ", postDTO);
            throw new RuntimeException("updatePosts ERROR " + postDTO);
        }

        postMapper.updatePosts(postDTO);
    }

    @Override
    public void deletePosts(int userId, int postId) {
        if (userId == 0 || postId==0) {
            log.error("deletePosts ERROR {}, {} ", userId, postId);
            throw new RuntimeException("deletePosts ERROR " + userId + postId);
        }

        postMapper.deletePosts(userId, postId);
    }
}
