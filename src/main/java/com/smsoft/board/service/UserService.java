package com.smsoft.board.service;

import com.smsoft.board.dto.UserDTO;

public interface UserService {

    void register(UserDTO userProfile);

    UserDTO login(String id, String password);

    boolean isDuplicatedId(String id);

    UserDTO getUserInfo(String id);
    void updatePassword(String id, String beforePassword, String afterPassword);

    void deleteId(String id, String password);
}
