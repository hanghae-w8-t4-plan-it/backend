package com.team4.planit.category;

import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByMember(Member member);
}
