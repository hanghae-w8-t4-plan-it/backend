package com.team4.planit.category;

import com.team4.planit.category.dto.CategoryRequestDto;
import com.team4.planit.category.dto.CategoryResponseDto;
import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import com.team4.planit.todoList.TodoListRepository;
import com.team4.planit.todo.TodoRepositorySupport;
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
    private final TodoRepositorySupport todoRepositorySupport;
    private final TodoListRepository todoListRepository;

    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = Category.builder()
                .member(member)
                .categoryName(requestDto.getCategoryName())
                .categoryColor(requestDto.getCategoryColor())
                .isPublic(false)
                .categoryStatus(CategoryStatusCode.NOT_STOP)
                .build();
        categoryRepository.save(category);
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryColor(category.getCategoryColor())
                .isPublic(category.getIsPublic())
                .categoryStatus(category.getCategoryStatus())
                .build();
    }

    @Transactional
    public List<CategoryResponseDto> getAllCategories(String dueDate, Long memberId, HttpServletRequest request) {
        if (memberId != null) getAllCategoriesOfOther(dueDate,memberId, request);
        Member member = check.validateMember(request);
        if(todoListRepository.findByMemberAndDueDate(member,dueDate).isEmpty()){
            throw new CustomException(ErrorCode.TODO_LIST_NOT_EXIST);
        }
        List<Category> categories = categoryRepository.findAllByMember(member);
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for (Category category : categories) {
            if (check.countByCategory(category) != 0 || category.getCategoryStatus().equals(CategoryStatusCode.NOT_STOP)) {
                categoryResponseDtoList.add(
                        CategoryResponseDto.builder()
                                .categoryId(category.getCategoryId())
                                .categoryName(category.getCategoryName())
                                .categoryColor(category.getCategoryColor())
                                .isPublic(category.getIsPublic())
                                .categoryStatus(category.getCategoryStatus())
                                .todos(todoRepositorySupport.findAllTodosByCategoryAndDueDate(category, dueDate))
                                .build()
                );
            }
        }
        return categoryResponseDtoList;
    }
    @Transactional
    public List<CategoryResponseDto> getAllCategoriesOfOther(String dueDate, Long memberId, HttpServletRequest request) {
        check.validateMember(request);
        Member member = check.isPresentMemberByMemberId(memberId);
        if(todoListRepository.findByMemberAndDueDate(member,dueDate).isEmpty()){
            throw new CustomException(ErrorCode.TODO_LIST_NOT_EXIST);
        }
        List<Category> categories = categoryRepository.findAllByMember(member);
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for (Category category : categories) {
            if (category.getIsPublic() && (check.countByCategory(category) != 0 ||
                    category.getCategoryStatus().equals(CategoryStatusCode.NOT_STOP))
            ) {
                categoryResponseDtoList.add(
                        CategoryResponseDto.builder()
                                .categoryId(category.getCategoryId())
                                .categoryName(category.getCategoryName())
                                .categoryColor(category.getCategoryColor())
                                .isPublic(category.getIsPublic())
                                .categoryStatus(category.getCategoryStatus())
                                .todos(todoRepositorySupport.findAllTodosByCategoryAndDueDate(category, dueDate))
                                .build()
                );
            }
        }
        return categoryResponseDtoList;
    }

    @Transactional
    public CategoryResponseDto updateCategory(CategoryRequestDto requestDto, Long categoryId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = check.isPresentCategory(categoryId);
        check.checkCategoryAuthor(member, category);
        category.update(requestDto);
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryColor(category.getCategoryColor())
                .isPublic(category.getIsPublic())
                .categoryStatus(category.getCategoryStatus())
                .build();
    }

    public void deleteCategory(Long categoryId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = check.isPresentCategory(categoryId);
        if(check.countByCategory(category)!=0) throw new CustomException(ErrorCode.CATEGORY_CANNOT_DELETE);
        check.checkCategoryAuthor(member, category);
        categoryRepository.delete(category);
    }
}
