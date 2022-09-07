package com.team4.planit.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto requestDto, HttpServletRequest request) {
        return categoryService.createCategory(requestDto, request);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategoriesOfOther(@RequestParam String date,
                                                     @RequestParam(required = false) Long member,
                                                     HttpServletRequest request) {
        if(member==null) return categoryService.getAllCategories(date, request);
        return categoryService.getAllCategoriesOfOther(date,member, request);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequestDto requestDto, @PathVariable Long categoryId, HttpServletRequest request) {
        return categoryService.updateCategory(requestDto, categoryId, request);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId, HttpServletRequest request) {
        return categoryService.deleteCategory(categoryId, request);
    }
}
