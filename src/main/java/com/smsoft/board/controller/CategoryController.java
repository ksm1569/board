package com.smsoft.board.controller;

import com.smsoft.board.aop.LoginCheck;
import com.smsoft.board.dto.CategoryDTO;
import com.smsoft.board.service.impl.CategoryServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void registerCategory(String userId, @RequestBody CategoryDTO categoryDTO) {
        categoryService.registerCategory(userId, categoryDTO);
    }

    @PatchMapping("{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void updateCategory(
            String userId,
            @PathVariable(name = "categoryId") int categoryId,
            @RequestBody CategoryRequest categoryRequest
    ) {
        // 변경예정
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(), CategoryDTO.SortStatus.NEWEST, 1, 1);
        categoryService.updateCategory(categoryDTO);
    }

    @DeleteMapping("{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void deleteCategory(String userId, @PathVariable(name = "categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @Getter
    @Setter
    private static class CategoryRequest {
        private int id;
        private String name;
    }

}
