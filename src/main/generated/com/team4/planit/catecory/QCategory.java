package com.team4.planit.catecory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = 1234717524L;

    public static final QCategory category = new QCategory("category");

    public final StringPath categoryColor = createString("categoryColor");

    public final StringPath categoryName = createString("categoryName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPublic = createBoolean("isPublic");

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

