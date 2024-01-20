package com.smsoft.board.service.impl;

import com.smsoft.board.dto.UserDTO;
import com.smsoft.board.exception.DuplicatedIdException;
import com.smsoft.board.mapper.UserProfileMapper;
import com.smsoft.board.service.UserService;
import com.smsoft.board.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserProfileMapper userProfileMapper;

    @Override
    public void register(UserDTO userDTO) {
        boolean duplicatedId = isDuplicatedId(userDTO.getUserId());
        if (duplicatedId) {
            log.error("Duplicated ID: {}", userDTO.getUserId());
            throw new DuplicatedIdException("Duplicated ID");
        }

        userDTO.setCreateDate(new Date());
        userDTO.setPassword(SHA256Util.encryptSHA256(userDTO.getPassword()));

        int insertCount = userProfileMapper.insertUserProfile(userDTO);

        if (insertCount != 1) {
            log.error("Failed to insert user profile: {}", userDTO.getUserId());
            throw new RuntimeException("Failed to insert user profile");
        }
    }

    @Override
    public UserDTO login(String id, String password) {
        UserDTO userDTO = userProfileMapper.findByIdAndPassword(id, SHA256Util.encryptSHA256(password));
        if (userDTO == null) {
            log.error("Invalid ID or password: {}", id);
            throw new RuntimeException("Invalid ID or password");
        }
        return userDTO;
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String id) {
        UserDTO userDTO = userProfileMapper.getUserProfile(id);
        if (userDTO == null) {
            log.error("User not found: {}", id);
            throw new RuntimeException("User not found");
        }
        return userProfileMapper.getUserProfile(id);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String beforeCryptoPassword = SHA256Util.encryptSHA256(beforePassword);
        UserDTO userDTO = userProfileMapper.findByIdAndPassword(id, beforeCryptoPassword);
        if (userDTO == null) {
            log.error("Invalid ID or password: {}, {}", id, beforePassword);
            throw new RuntimeException("Invalid ID or password");
        }

        String afterCryptoPassword = SHA256Util.encryptSHA256(afterPassword);
        userDTO.setPassword(afterCryptoPassword);

        int updateCount = userProfileMapper.updatePassword(userDTO);
        if (updateCount != 1) {
            log.error("Failed to update password for user: {}", id);
            throw new RuntimeException("Failed to update password");
        }
    }

    @Override
    public void deleteId(String id, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        UserDTO userDTO = userProfileMapper.findByIdAndPassword(id, cryptoPassword);
        if (userDTO == null) {
            log.error("Invalid ID or password: {}", id);
            throw new RuntimeException("Invalid ID or password");
        }

        int deleteCount = userProfileMapper.deleteUserProfile(id);
        if (deleteCount != 1) {
            log.error("Failed to delete user profile: {}", id);
            throw new RuntimeException("Failed to delete user profile");
        }
    }
}
