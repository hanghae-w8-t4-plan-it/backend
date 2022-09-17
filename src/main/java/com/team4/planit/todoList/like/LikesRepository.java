package com.team4.planit.todoList.like;

import com.team4.planit.member.Member;
import com.team4.planit.todoList.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberAndAndTodoList(Member member, TodoList todoList);

    @Query(value = "select likes_count, todo_list_due_date\n" +
            "from (\n" +
            "        select tl.todo_list_due_date, count(*) as likes_count,\n" +
            "               rank() over (order by count(*) desc) as max_likes_rank\n" +
            "        from likes l\n" +
            "        inner join todo_list tl\n" +
            "        on l.todo_list_id = tl.todo_list_id\n" +
            "        where tl.member_id = 3 and tl.todo_list_due_date like '2022-09%'\n" +
            "        group by tl.todo_list_id\n" +
            "    ) as result\n" +
            "where max_likes_rank = 1;", nativeQuery = true)
    List<String> findTopLikeDates(@Param("memberId") Long memberId, @Param("month") String month);

    @Query(value = "select likes_count, member_nickname\n" +
            "from (\n" +
            "    select m.member_nickname, count(*) as likes_count,\n" +
            "           rank() over (order by count(*) desc) as max_likes_rank\n" +
            "    from likes l\n" +
            "    inner join todo_list tl\n" +
            "    on l.todo_list_id = tl.todo_list_id\n" +
            "    inner join member m\n" +
            "    on l.member_id = m.member_id\n" +
            "    where tl.member_id = :memberId and tl.todo_list_due_date like :month%\n" +
            "    group by l.member_id\n" +
            ") as result\n" +
            "where max_likes_rank = 1", nativeQuery = true)
    List<String> findTopLikeMembers(@Param("memberId") Long memberId, @Param("month") String month);
}
