package com.smsoft.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDTO {
    public static boolean hasNullDataBeforeSignUp(UserDTO userDTO) {
        return userDTO.getUserId() == null || userDTO.getPassword() == null || userDTO.getNickName() == null;
    }

    public enum Status {
        DEFAULT, ADMIN, DELETED
    }
    
    private int id;
    private String userId;
    private String password;
    private String nickName;
    private Boolean isAdmin;
    private Date createDate;
    private Boolean isWithDraw;
    private Status status;
    private Date updateDate;
}
