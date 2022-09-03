package com.team4.planit.category;

import com.team4.planit.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "category_color", nullable = false)
    private String categoryColor;

    @Column(name = "category_is_public", nullable = false)
    private Boolean isPublic;

    @Column(name = "category_status", nullable = false)
    private CategoryStatusCode categoryStatus;

    @Builder
    public Category(Member member, String categoryName, String categoryColor, Boolean isPublic, CategoryStatusCode categoryStatus) {
        this.member = member;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.isPublic = isPublic;
        this.categoryStatus = categoryStatus;
    }

    public void update(CategoryRequestDto requestDto) {
        if(requestDto.getCategoryName()!=null) this.categoryName = requestDto.getCategoryName();
        if(requestDto.getCategoryColor()!=null) this.categoryColor = requestDto.getCategoryColor();
        if(requestDto.getIsPublic()!=null) this.isPublic = requestDto.getIsPublic();
        if(requestDto.getCategoryStatus()!=null) this.categoryStatus = requestDto.getCategoryStatus();
    }
}
