package com.team4.planit.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JoinColumn(name = "member_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member member;

    @Column
    private String categoryName;

    @Column
    private String categoryColor;

    @Column
    private Boolean isPublic;

    public Category(String categoryName, String categoryColor, Boolean isPublic) {
//        this.member = member;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.isPublic = isPublic;
    }

//    public Category(String categoryName, String categoryColor, Boolean isPublic) {
//        this.categoryName = categoryName;
//        this.categoryColor = categoryColor;
//        this.isPublic = isPublic;
//    }
}
