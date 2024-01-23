package com.smsoft.board.dto;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private int id;
    private String title;
    private Boolean isAdmin;
    private String contents;
    private Date createDate;
    private int viewCount;
    private int userId;
    private int categoryId;
    private int fileId;
    private Date updateDate;
}
