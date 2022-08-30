package com.team4.planit.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto requestDto, HttpServletRequest request) {
        return categoryService.createCategory(requestDto, request);
    }

    @GetMapping
    public ResponseEntity<?> getCategory(HttpServletRequest request) {
        return categoryService.getCategory(request);
    }
}
