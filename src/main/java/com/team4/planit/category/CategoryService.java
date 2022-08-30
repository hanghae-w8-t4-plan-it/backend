package com.team4.planit.category;

import com.team4.planit.Message;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
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

    @Transactional
    public ResponseEntity<?> createCategory(CategoryRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.accessTokenCheck(request, member);
        Category category = Category.builder()
                .member(member)
                .categoryName(requestDto.getCategoryName())
                .categoryColor(requestDto.getCategoryColor())
                .isPublic(false)
                .isEnd(false)
                .build();
        categoryRepository.save(category);
        return new ResponseEntity<>(Message.success(CategoryResponseDto.builder()
                        .id(category.getId())
                        .categoryName(category.getCategoryName())
                        .categoryColor(category.getCategoryColor())
                        .isPublic(category.getIsPublic())
                        .isEnd(category.getIsEnd())
                .build()), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCategory(HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.accessTokenCheck(request, member);
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryResponseDtoList.add(
                    CategoryResponseDto.builder()
                            .id(category.getId())
                            .categoryName(category.getCategoryName())
                            .categoryColor(category.getCategoryColor())
                            .isPublic(category.getIsPublic())
                            .isEnd(category.getIsEnd())
                            .build()
            );
        }
        return new ResponseEntity<>(Message.success(categoryResponseDtoList), HttpStatus.OK);
    }
}
