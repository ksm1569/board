package com.smsoft.board.service;

import com.smsoft.board.dto.PostDTO;

import java.util.List;

public interface PostService {
    void registerPosts(String userId, PostDTO postDTO);
    List<PostDTO> getMyPosts(int userId);
    void updatePosts(PostDTO postDTO);
    void deletePosts(int userId, int postId);
}
