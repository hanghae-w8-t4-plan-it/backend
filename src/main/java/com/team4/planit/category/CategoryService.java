package com.team4.planit.category;

import com.team4.planit.global.shared.Message;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.todoList.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final Check check;
    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public ResponseEntity<?> createCategory(CategoryRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.checkAccessToken(request, member);
        Category category = Category.builder()
                .member(member)
                .categoryName(requestDto.getCategoryName())
                .categoryColor(requestDto.getCategoryColor())
                .isPublic(false)
                .categoryStatues(CategoryStatusCode.NOT_STOP)
                .build();
        categoryRepository.save(category);
        return new ResponseEntity<>(Message.success(CategoryResponseDto.builder()
                        .id(category.getId())
                        .categoryName(category.getCategoryName())
                        .categoryColor(category.getCategoryColor())
                        .isPublic(category.getIsPublic())
                        .categoryStatues(category.getCategoryStatues())
                .build()), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCategory(HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.checkAccessToken(request, member);
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for (Category category : categoryList) {
//            if(!category.getCategoryStatues().equals(CategoryStatusCode.ACHIEVE)||equals(CategoryStatusCode.EXPIRE)||equals(CategoryStatusCode.STOP)) {
                categoryResponseDtoList.add(
                        CategoryResponseDto.builder()
                                .id(category.getId())
                                .categoryName(category.getCategoryName())
                                .categoryColor(category.getCategoryColor())
                                .isPublic(category.getIsPublic())
                                .categoryStatues(category.getCategoryStatues())
                                .build()
                );
//            } else { throw new CustomException(ErrorCode.CATEGORY_END); }
        }
        return new ResponseEntity<>(Message.success(categoryResponseDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateCategory(CategoryRequestDto requestDto, Long categoryId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = check.isPresentCategory(categoryId);
        check.checkAccessToken(request, member);
        check.checkCategory(category);
        check.checkCategoryAuthor(member, category);
        category.update(requestDto);
        return new ResponseEntity<>(Message.success(CategoryResponseDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .categoryColor(category.getCategoryColor())
                .isPublic(category.getIsPublic())
                .categoryStatues(category.getCategoryStatues())
                .build()), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteCategory(Long categoryId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = check.isPresentCategory(categoryId);
        check.checkCategory(category);
        check.checkCategoryAuthor(member, category);
        categoryRepository.delete(category);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
