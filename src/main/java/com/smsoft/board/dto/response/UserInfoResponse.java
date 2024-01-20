package com.smsoft.board.dto.response;

import com.smsoft.board.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private UserDTO userDTO;
}
