package com.smsoft.board.mapper;

import com.smsoft.board.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    public int registerPosts(PostDTO postDTO);
    public List<PostDTO> getMyPosts(int userId);
    public void updatePosts(PostDTO postDTO);
    public void deletePosts(int userId, int postId);
}
