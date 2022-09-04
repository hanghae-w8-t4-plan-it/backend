package com.team4.planit.category;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team4.planit.category.QCategory.category;
import static com.team4.planit.todoList.QTodo.todo;

@Repository
public class CategoryRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public CategoryRepositorySupport(JPAQueryFactory queryFactory) {
        super(Category.class);
        this.queryFactory = queryFactory;
    }

    public List<CategoryResponseDto> findAllCategories() {
        return queryFactory
                .select(Projections.fields(
                        CategoryResponseDto.class,
                        category.categoryId,
                        category.categoryName,
                        category.categoryColor,
                        category.isPublic,
                        category.categoryStatus
                ))
                .from(category)
                .leftJoin(todo)
                .fetchJoin()
                .on(category.categoryId.eq(todo.category.categoryId))
                .where(category.eq(todo.category))
                .groupBy(category)
                .fetch();
    }

//    //카테고리 별 조회
//    public List<HouseMainResponseDto> findAllByCategory(Category category) {
//        return queryFactory
//                .select(Projections.fields(
//                        HouseMainResponseDto.class,
//                        house.houseId,
//                        house.category,
//                        house.title,
//                        house.nation,
//                        house.price,
//                        house.starAvg,
//                        houseImg.imgUrl
//                ))
//                .from(house)
//                .leftJoin(houseImg)
//                .on(house.houseId.eq(houseImg.house.houseId))
//                .where(house.category.eq(category))
//                .groupBy(house.houseId)
//                .fetch();
//    }
//
//    //전체 조회
//    public List<HouseMainResponseDto> findAllByOrderByModifiedAtDesc() {
//        return queryFactory
//                .select(Projections.fields(
//                        HouseMainResponseDto.class,
//                        house.houseId,
//                        house.category,
//                        house.title,
//                        house.nation,
//                        house.price,
//                        house.starAvg,
//                        houseImg.HouseImgId,
//                        houseImg.imgUrl
//                ))
//                .from(house)
//                .leftJoin(houseImg)
//                .on(house.houseId.eq(houseImg.house.houseId))
//                .groupBy(house.houseId)
//                .fetch();
//
//    }
//
//    public List<HouseMainResponseDto> findAllByFilter(int minPrice, int maxPrice,
//                                                      int bedRoomCnt, int bedCnt, List<FacilityType> facilities) {
//
//        return queryFactory
//                .select(Projections.fields(
//                        HouseMainResponseDto.class,
//                        house.houseId,
//                        house.category,
//                        house.title,
//                        house.nation,
//                        house.price,
//                        house.starAvg,
//                        houseImg.imgUrl
//                ))
//                .from(house)
//                .leftJoin(facility)
//                .on(house.houseId.eq(facility.house.houseId))
//                .leftJoin(houseImg)
//                .on(house.houseId.eq(houseImg.house.houseId))
//                .where(betweenPrice(minPrice, maxPrice),
//                        eqBedRoomCnt(bedRoomCnt),
//                        eqBedCnt(bedCnt),
//                        eqFacilities(facilities))
//                .orderBy(house.houseId.desc())
//                .groupBy(house.houseId)
//                .fetch();
//    }
//
//    private BooleanExpression betweenPrice(int minPrice, int maxPrice) {
//        if (minPrice == 0 && maxPrice == 0) return null;
//        return house.price.between(minPrice, maxPrice);
//    }
//
//    private BooleanExpression eqBedRoomCnt(int bedRoomCnt) {
//        if (bedRoomCnt == 0) return null;
//        return house.bedRoomCnt.eq(bedRoomCnt);
//    }
//
//    private BooleanExpression eqBedCnt(int bedCnt) {
//        if (bedCnt == 0) return null;
//        return house.bedRoomCnt.eq(bedCnt);
//    }
//
//    private BooleanExpression eqFacilities(List<FacilityType> facilities) {
//        if (facilities.isEmpty()) return null;
//        return facility.facilityType.in(facilities);
//    }
}