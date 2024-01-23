package com.smsoft.board.service.impl;

import com.smsoft.board.dto.CategoryDTO;
import com.smsoft.board.mapper.CategoryMapper;
import com.smsoft.board.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;

    @Override
    public void registerCategory(String userId, CategoryDTO categoryDTO) {
        if (userId == null) {
            log.error("register ERROR {} ", categoryDTO);
            throw new RuntimeException("register ERROR " + categoryDTO);
        }

        categoryMapper.registerCategory(categoryDTO);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            log.error("update ERROR {} ", categoryDTO);
            throw new RuntimeException("update ERROR " + categoryDTO);
        }

        categoryMapper.updateCategory(categoryDTO);
    }

    @Override
    public void deleteCategory(int categoryId) {
        if (categoryId == 0) {
            log.error("delete ERROR {} ", categoryId);
            throw new RuntimeException("delete ERROR " + categoryId);
        }

        categoryMapper.deleteCategory(categoryId);
    }
}
