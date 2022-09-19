package com.team4.planit.category;

import com.team4.planit.category.dto.CategoryRequestDto;
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
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(requestDto, request);
        return new ResponseEntity<>(Message.success(categoryResponseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategoriesOfOther(@RequestParam String date,
                                                     @RequestParam(required = false) Long memberId,
                                                     HttpServletRequest request) {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getAllCategories(date, memberId, request);
        return new ResponseEntity<>(Message.success(categoryResponseDtoList), HttpStatus.OK);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequestDto requestDto, @PathVariable Long categoryId, HttpServletRequest request) {
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(requestDto, categoryId, request);
        return new ResponseEntity<>(Message.success(categoryResponseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId, HttpServletRequest request) {
        categoryService.deleteCategory(categoryId, request);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
