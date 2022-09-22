package com.team4.planit.category;

import com.team4.planit.category.dto.CategoryDetailResponseDto;
import com.team4.planit.category.dto.CategoryRequestDto;
import com.team4.planit.category.dto.CategoryResponseDto;
import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.todo.TodoRepositorySupport;
import com.team4.planit.todoList.TodoList;
import com.team4.planit.todoList.TodoListRepository;
import lombok.RequiredArgsConstructor;
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
    public CategoryDetailResponseDto createCategory(CategoryRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = Category.builder()
                .member(member)
                .categoryName(requestDto.getCategoryName())
                .categoryColor(requestDto.getCategoryColor())
                .isPublic(false)
                .categoryStatus(CategoryStatusCode.NOT_STOP)
                .build();
        categoryRepository.save(category);
        return CategoryDetailResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryColor(category.getCategoryColor())
                .isPublic(category.getIsPublic())
                .categoryStatus(category.getCategoryStatus())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getCategoryMenus(HttpServletRequest request) {
        Member member = check.validateMember(request);
        List<Category> categories = categoryRepository.findAllByMember(member);
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for (Category category : categories) {
            categoryResponseDtoList.add(
            CategoryResponseDto.builder()
                    .categoryId(category.getCategoryId())
                    .categoryName(category.getCategoryName())
                    .isPublic(category.getIsPublic())
                    .categoryColor(category.getCategoryColor())
                    .categoryStatus(category.getCategoryStatus())
                    .build());
        }
        return categoryResponseDtoList;
    }

    @Transactional(readOnly = true)
    public List<CategoryDetailResponseDto> getAllCategories(String dueDate, Long memberId, HttpServletRequest request) {
        if (memberId != null) return getAllCategoriesOfOther(dueDate, memberId, request);
        Member member = check.validateMember(request);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, dueDate)
                .orElseGet(() -> new TodoList(member, dueDate, (byte) 0));
        List<Category> categories = categoryRepository.findAllByMember(member);
        List<CategoryDetailResponseDto> categoryDetailResponseDtoList = new ArrayList<>();
        for (Category category : categories) {
            if (check.countByCategory(category) != 0 || category.getCategoryStatus().equals(CategoryStatusCode.NOT_STOP)) {
                categoryDetailResponseDtoList.add(
                        CategoryDetailResponseDto.builder()
                                .todoListId(todoList.getTodoListId())
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


        return categoryDetailResponseDtoList;
    }

    @Transactional(readOnly = true)
    public List<CategoryDetailResponseDto> getAllCategoriesOfOther(String dueDate, Long memberId, HttpServletRequest request) {
        check.validateMember(request);
        Member member = check.isPresentMemberByMemberId(memberId);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, dueDate)
                .orElseGet(() -> new TodoList(member, dueDate, (byte) 0));
        List<Category> categories = categoryRepository.findAllByMember(member);
        List<CategoryDetailResponseDto> categoryDetailResponseDtoList = new ArrayList<>();
        for (Category category : categories) {
            if (category.getIsPublic() && (check.countByCategory(category) != 0 ||
                    category.getCategoryStatus().equals(CategoryStatusCode.NOT_STOP))
            ) {
                categoryDetailResponseDtoList.add(
                        CategoryDetailResponseDto.builder()
                                .todoListId(todoList.getTodoListId())
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
        return categoryDetailResponseDtoList;
    }

    @Transactional
    public CategoryDetailResponseDto updateCategory(CategoryRequestDto requestDto, Long categoryId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = check.isPresentCategory(categoryId);
        check.checkCategoryAuthor(member, category);
        category.update(requestDto);
        return CategoryDetailResponseDto.builder()
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
