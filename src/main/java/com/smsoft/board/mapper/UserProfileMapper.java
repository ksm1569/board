package com.smsoft.board.mapper;

import com.smsoft.board.dto.UserDTO;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {
    public UserDTO getUserProfile(@Param("userId") String userId);

    int insertUserProfile(UserDTO userDTO);

    int updateUserProfile(UserDTO userDTO);

    int deleteUserProfile(@Param("userId") String userId);

    public UserDTO findByIdAndPassword(
            @Param("userId") String userId,
            @Param("password") String password
    );

    public int idCheck(@Param("userId") String userId);

    public int updatePassword(UserDTO userDTO);

}
