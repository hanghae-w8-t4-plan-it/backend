package com.team4.planit.category;

import com.team4.planit.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> createCategory(CategoryRequestDto requestDto, HttpServletRequest request) {
//        Member member = check.validateMember(request);   나중에 받아올거임
        Category category = new Category(requestDto.getCategoryName(), requestDto.getCategoryColor(), requestDto.getIsPublic());
        categoryRepository.save(category);
        return new ResponseEntity<>(Message.success(CategoryResponsDto.builder()
                        .id(category.getId())
                        .categoryName(category.getCategoryName())
                        .categoryColor(category.getCategoryColor())
                        .isPublic(category.getIsPublic())
                .build()), HttpStatus.OK);
    }

    public ResponseEntity<?> getCategory(HttpServletRequest request) {
//        Member member = check.validateMember(request);
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponsDto> categoryResponseDtoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryResponseDtoList.add(
                    CategoryResponsDto.builder()
                            .id(category.getId())
                            .categoryName(category.getCategoryName())
                            .categoryColor(category.getCategoryColor())
                            .isPublic(category.getIsPublic())
                            .build()
            );
        }
        return new ResponseEntity<>(Message.success(categoryResponseDtoList), HttpStatus.OK);
    }
}
