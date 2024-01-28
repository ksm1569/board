package com.smsoft.board.mapper;

import com.smsoft.board.dto.PostDTO;
import com.smsoft.board.dto.request.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostSearchMapper {
    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);
}
