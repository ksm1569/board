package com.smsoft.board.dto.request;

import com.smsoft.board.dto.CategoryDTO;
import lombok.*;

import java.util.Date;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchRequest {
    private int id;
    private String title;
    private String contents;
    private Date createDate;
    private int viewCount;
    private int userId;
    private int categoryId;
    private CategoryDTO.SortStatus sortStatus;
}
