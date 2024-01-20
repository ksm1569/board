package com.smsoft.board.dto.response;

import com.smsoft.board.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    enum LoginStatus {
        SUCCESS, FAIL, DELETED
    }

    @NonNull
    private LoginStatus loginStatus;
    private UserDTO userDTO;

    private static final UserLoginResponse FAIL = new UserLoginResponse(LoginStatus.FAIL);

    public static UserLoginResponse createSuccessResponse(UserDTO userDTO) {
        return new UserLoginResponse(LoginStatus.SUCCESS, userDTO);
    }
}
