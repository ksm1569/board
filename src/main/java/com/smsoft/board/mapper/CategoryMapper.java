package com.smsoft.board.mapper;

import com.smsoft.board.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    public int registerCategory(CategoryDTO categoryDTO);
    public void updateCategory(CategoryDTO categoryDTO);
    public void deleteCategory(int categoryId);
}
