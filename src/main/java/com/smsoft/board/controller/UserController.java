package com.smsoft.board.controller;

import com.smsoft.board.aop.LoginCheck;
import com.smsoft.board.dto.UserDTO;
import com.smsoft.board.dto.request.UserDeleteId;
import com.smsoft.board.dto.request.UserLoginRequest;
import com.smsoft.board.dto.request.UserUpdatePasswordRequest;
import com.smsoft.board.dto.response.UserInfoResponse;
import com.smsoft.board.dto.response.UserLoginResponse;
import com.smsoft.board.service.impl.UserServiceImpl;
import com.smsoft.board.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;
    private static UserLoginResponse userLoginResponse;
    @PostMapping("/sign-up")
    public void signUp(@RequestBody UserDTO userDTO) {
        if (UserDTO.hasNullDataBeforeSignUp(userDTO)) {
            throw new IllegalArgumentException("UserDTO has null data before sign up");
        }
        userService.register(userDTO);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest, HttpSession httpSession) {
        String id = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();

        UserDTO userDTO = userService.login(userLoginRequest.getUserId(), userLoginRequest.getPassword());

        if (userDTO == null) {
            return new ResponseEntity<>(userLoginResponse, HttpStatus.NOT_FOUND);
        }

        userLoginResponse = UserLoginResponse.createSuccessResponse(userDTO);

        if(userDTO.getStatus() == (UserDTO.Status.ADMIN)) {
            SessionUtil.setLoginAdminId(httpSession, id);
        } else {
            SessionUtil.setLoginMemberId(httpSession, id);
        }

        return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
    }

    @GetMapping("/my-info")
    public UserInfoResponse userInfo(HttpSession httpSession) {
        String id = SessionUtil.getLoginAdminId(httpSession);
        if (id == null) id = SessionUtil.getLoginMemberId(httpSession);
        UserDTO userInfo = userService.getUserInfo(id);
        return new UserInfoResponse(userInfo);
    }

    // 비밀번호만 변경 할때는 PatchMapping이 더 적절하다.
    @PatchMapping("/password")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<UserLoginResponse> updateUserPassword(
            String userId,
            @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
            HttpSession httpSession
    ) {
        String id = userId;
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try {
            userService.updatePassword(id, beforePassword, afterPassword);

        }catch (IllegalArgumentException e) {
            log.error("Failed to update password: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<UserLoginResponse>(userLoginResponse, HttpStatus.OK);
    }

    @PutMapping("/logout")
    public void logout(HttpSession httpSession) {
        SessionUtil.clear(httpSession);
    }

    @DeleteMapping
    public ResponseEntity<UserLoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId) {
        try {
            userService.deleteId(userDeleteId.getId(), userDeleteId.getPassword());
        } catch (IllegalArgumentException e) {
            log.error("Failed to delete id: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<UserLoginResponse>(userLoginResponse, HttpStatus.OK);
    }
}
