package com.team4.planit.todoList;

import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.member.MemberRepository;
import com.team4.planit.todoList.dto.DailyTodoListResponseDto;
import com.team4.planit.todoList.dto.TodoListRequestDto;
import com.team4.planit.todoList.dto.WeeklyPlanetResponseDto;
import com.team4.planit.todoList.dto.WeeklyTodoListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoListService {
    private final Check check;
    private final MemberRepository memberRepository;
    private final TodoListRepositorySupport todoListRepositorySupport;
    private final TodoListRepository todoListRepository;

    @Transactional
    public DailyTodoListResponseDto updatePlanetType(String dueDate, Byte planetType, HttpServletRequest request) {
        Member member = check.validateMember(request);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, dueDate)
                .orElseGet(() -> new TodoList(member, dueDate, (byte) 0));
        todoList.update(planetType);
        todoListRepository.save(todoList);
        return getDailyTodoList(member.getMemberId(), dueDate,  request);
    }

    @Transactional(readOnly = true)
    public List<String> getUnAchievedDueDatesByYearAndMonth(String year, String month, HttpServletRequest request) {
        Member member = check.validateMember(request);
        return todoListRepositorySupport.findUnAchievedDueDatesByMemberAndYearAndMonth(member, year, month);
    }

    @Transactional(readOnly = true)
    public DailyTodoListResponseDto getDailyTodoList(Long memberId, String dueDate, HttpServletRequest request) {
        Member member = check.validateMember(request);
        if (memberId != null) member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        DailyTodoListResponseDto dailyTodoListResponseDto = todoListRepositorySupport.findDailyTodoListByMemberAndDueDate(member, dueDate);
        if (dailyTodoListResponseDto == null) {
            TodoList todoList = todoListRepository.findByMemberAndDueDate(member, dueDate).orElse(null);
            dailyTodoListResponseDto = DailyTodoListResponseDto.builder()
                    .todoListId(todoList.getTodoListId())
                    .dueDate(todoList.getDueDate())
                    .planetType(todoList.getPlanetType())
                    .planetSize(todoList.getPlanetSize())
                    .planetColor(todoList.getPlanetColor())
                    .planetLevel(todoList.getPlanetLevel())
                    .achievementCnt(0)
                    .likesCnt(0L)
                    .build();
        }
        return dailyTodoListResponseDto;
    }

    @Transactional(readOnly = true)
    public WeeklyTodoListResponseDto getWeeklyTodoList(Long memberId, String startDate, HttpServletRequest request)
            throws ParseException {
        Member member = check.validateMember(request);
        if (memberId != null) member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        cal.add(Calendar.DATE, 6);
        String endDate = sdf.format(cal.getTime());
        List<TodoList> planets = todoListRepositorySupport.findWeeklyPlanet(member, startDate, endDate);
        return WeeklyTodoListResponseDto.builder()
                .nickname(member.getNickname())
                .weeklyTotalAchievement(todoListRepositorySupport.findWeeklyTotalAchievement(member, startDate, endDate))
                .weeklyTotalLikes(todoListRepositorySupport.findWeeklyTotalLikes(member, startDate, endDate))
                .planets(makeWeeklyPlanetResponseDtoList(startDate, planets))
                .build();
    }

    private List<WeeklyPlanetResponseDto> makeWeeklyPlanetResponseDtoList(String startDate, List<TodoList> planets) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        List<WeeklyPlanetResponseDto> dailyTodoListResponseDtoList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            cal.setTime(sdf.parse(startDate));
            cal.add(Calendar.DATE, i);
            dailyTodoListResponseDtoList.add(new WeeklyPlanetResponseDto(sdf.format(cal.getTime())));
            for (TodoList planet : planets) {
                if (dailyTodoListResponseDtoList.get(i).getDueDate().equals(planet.getDueDate())) {
                    dailyTodoListResponseDtoList.set(i, WeeklyPlanetResponseDto.builder()
                            .todoListId(planet.getTodoListId())
                            .dueDate(planet.getDueDate())
                            .planetType(planet.getPlanetType())
                            .planetSize(planet.getPlanetSize())
                            .planetColor(planet.getPlanetColor())
                            .planetLevel(planet.getPlanetLevel())
                            .build());
                }
            }
        }
        return dailyTodoListResponseDtoList;
    }

    @Transactional
    public DailyTodoListResponseDto updatePlanet(TodoListRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, requestDto.getDueDate()).orElseThrow(
                () -> new CustomException(ErrorCode.TODO_LIST_NOT_FOUND)
        );
        check.checkTodoListAuthor(member, todoList);
        todoList.update(requestDto.getPlanetSize(), requestDto.getPlanetColor());
        return DailyTodoListResponseDto.builder()
                .dueDate(todoList.getDueDate())
                .planetType(todoList.getPlanetType())
                .planetSize(todoList.getPlanetSize())
                .planetColor(todoList.getPlanetColor())
                .planetLevel(todoList.getPlanetLevel())
                .build();
    }
}
