package com.smsoft.board.service.impl;

import com.smsoft.board.dto.PostDTO;
import com.smsoft.board.dto.request.PostSearchRequest;
import com.smsoft.board.mapper.PostSearchMapper;
import com.smsoft.board.service.PostSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class PostSearchServiceImpl implements PostSearchService {
    private final PostSearchMapper postSearchMapper;

    @Cacheable(value = "getPosts", key ="'getPosts' + #postSearchRequest.getTitle() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest) {
        return postSearchMapper.selectPosts(postSearchRequest);
    }
}
