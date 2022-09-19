package com.team4.planit.category;

import com.team4.planit.category.dto.CategoryRequestDto;
import com.team4.planit.category.dto.CategoryDetailResponseDto;
import com.team4.planit.category.dto.CategoryResponseDto;
import com.team4.planit.global.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto requestDto, HttpServletRequest request) {
        CategoryDetailResponseDto categoryDetailResponseDto = categoryService.createCategory(requestDto, request);
        return new ResponseEntity<>(Message.success(categoryDetailResponseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategoriesOfOther(@RequestParam String date,
                                                     @RequestParam(required = false) Long memberId,
                                                     HttpServletRequest request) {
        List<CategoryDetailResponseDto> categoryDetailResponseDtoList = categoryService.getAllCategories(date, memberId, request);
        return new ResponseEntity<>(Message.success(categoryDetailResponseDtoList), HttpStatus.OK);
    }

    @GetMapping("/menu")
    public ResponseEntity<?> getCategoryMenus(HttpServletRequest request) {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getCategoryMenus(request);
        return new ResponseEntity<>(Message.success(categoryResponseDtoList), HttpStatus.OK);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequestDto requestDto, @PathVariable Long categoryId, HttpServletRequest request) {
        CategoryDetailResponseDto categoryDetailResponseDto = categoryService.updateCategory(requestDto, categoryId, request);
        return new ResponseEntity<>(Message.success(categoryDetailResponseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId, HttpServletRequest request) {
        categoryService.deleteCategory(categoryId, request);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
