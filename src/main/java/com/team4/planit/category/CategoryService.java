package com.team4.planit.category;

import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import com.team4.planit.todoList.TodoList;
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
    public ResponseEntity<?> createCategory(CategoryRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = Category.builder()
                .member(member)
                .categoryName(requestDto.getCategoryName())
                .categoryColor(requestDto.getCategoryColor())
                .isPublic(false)
                .categoryStatus(CategoryStatusCode.NOT_STOP)
                .build();
        categoryRepository.save(category);
        return new ResponseEntity<>(Message.success(CategoryResponseDto.builder()
                        .categoryId(category.getCategoryId())
                        .categoryName(category.getCategoryName())
                        .categoryColor(category.getCategoryColor())
                        .isPublic(category.getIsPublic())
                        .categoryStatus(category.getCategoryStatus())
                .build()), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllCategories(String dueDate, HttpServletRequest request) {
        Member member = check.validateMember(request);
        if(todoListRepository.findByMemberAndDueDate(member,dueDate).isEmpty()){
           todoListRepository.save(new TodoList(member, dueDate));
        }
        List<Category> categories = categoryRepository.findAllByMember(member);
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for (Category category : categories) {
            if (check.countByCategory(category) != 0 || category.getCategoryStatus().equals(CategoryStatusCode.NOT_STOP) ||
                    category.getCategoryStatus().equals(CategoryStatusCode.RESTART)) {
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
        return new ResponseEntity<>(Message.success(categoryResponseDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateCategory(CategoryRequestDto requestDto, Long categoryId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = check.isPresentCategory(categoryId);
        check.checkCategoryAuthor(member, category);
        category.update(requestDto);
        return new ResponseEntity<>(Message.success(CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryColor(category.getCategoryColor())
                .isPublic(category.getIsPublic())
                .categoryStatus(category.getCategoryStatus())
                .build()), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteCategory(Long categoryId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = check.isPresentCategory(categoryId);
        check.checkCategoryAuthor(member, category);
        categoryRepository.delete(category);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
