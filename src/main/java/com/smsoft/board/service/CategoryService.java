package com.smsoft.board.service;

import com.smsoft.board.dto.CategoryDTO;

public interface CategoryService {
    void registerCategory(String userId, CategoryDTO categoryDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void deleteCategory(int categoryId);
}
