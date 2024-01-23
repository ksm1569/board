package com.smsoft.board.controller;

import com.mysql.cj.log.Log;
import com.smsoft.board.aop.LoginCheck;
import com.smsoft.board.dto.PostDTO;
import com.smsoft.board.dto.UserDTO;
import com.smsoft.board.dto.response.CommonResponse;
import com.smsoft.board.service.PostService;
import com.smsoft.board.service.UserService;
import com.smsoft.board.service.impl.PostServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final UserService userService;
    private final PostServiceImpl postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPosts(String userId, @RequestBody PostDTO postDTO) {
        postService.registerPosts(userId, postDTO);

        // 리펙토링 필요
        HttpStatus status = HttpStatus.CREATED;
        String successCode = "SUCCESS";
        String successMessage = "registerPosts successfully created.";

        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(status, successCode, successMessage, postDTO);

        return new ResponseEntity<>(commonResponse, status);
    }

    @GetMapping("/my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> myPostInfo(String userId) {
        UserDTO userInfo = userService.getUserInfo(userId);
        List<PostDTO> myPosts = postService.getMyPosts(userInfo.getId());

        HttpStatus status = HttpStatus.OK;
        String successCode = "SUCCESS";
        String successMessage = "myPostInfo successfully created.";

        CommonResponse<List<PostDTO>> commonResponse = new CommonResponse<>(status, successCode, successMessage, myPosts);

        return new ResponseEntity<>(commonResponse, status);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostUpdateRequest>> updatePosts(
            String userId,
            @PathVariable(name = "postId") int postId,
            @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        UserDTO userInfo = userService.getUserInfo(userId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .title(postUpdateRequest.getTitle())
                .contents(postUpdateRequest.getContents())
                .viewCount(postUpdateRequest.getViewCount())
                .categoryId(postUpdateRequest.getCategoryId())
                .userId(postUpdateRequest.getUserId())
                .fileId(postUpdateRequest.getFileId())
                .build();

        postService.updatePosts(postDTO);

        // 리펙토링 필요 -> 코드만 보면 너무 짜증난다.
        HttpStatus status = HttpStatus.OK;
        String successCode = "SUCCESS";
        String successMessage = "myPostInfo successfully created.";

        CommonResponse<PostUpdateRequest> commonResponse = new CommonResponse<>(status, successCode, successMessage, postUpdateRequest);

        return new ResponseEntity<>(commonResponse, status);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deletePosts(
            String userId,
            @PathVariable(name = "postId") int postId,
            @RequestBody PostDeleteRequest postDeleteRequest
    ) {
        UserDTO userInfo = userService.getUserInfo(userId);
        postService.deletePosts(userInfo.getId(), postId);

        HttpStatus status = HttpStatus.OK;
        String successCode = "SUCCESS";
        String successMessage = "myPostInfo successfully created.";

        CommonResponse<PostDeleteRequest> commonResponse = new CommonResponse<>(status, successCode, successMessage, postDeleteRequest);

        return new ResponseEntity<>(commonResponse, status);
    }


    @Getter
    @AllArgsConstructor
    private static class PostUpdateResponse {
        private List<PostDTO> postDTOs;
    }

    @Getter
    @Setter
    private static class PostUpdateRequest {
        private String title;
        private String contents;
        private int viewCount;
        private int categoryId;
        private int userId;
        private int fileId;
    }

    @Getter
    @Setter
    private static class PostDeleteRequest {
        private int userId;
        private int postId;
    }
}
