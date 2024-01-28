package com.smsoft.board.service;

import com.smsoft.board.dto.PostDTO;
import com.smsoft.board.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchService {
    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);
}
