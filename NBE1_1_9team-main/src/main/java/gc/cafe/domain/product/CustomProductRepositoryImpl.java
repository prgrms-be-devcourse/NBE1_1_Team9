package gc.cafe.domain.product;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static gc.cafe.domain.product.QProduct.*;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findProductByNameOrCategory(String name, String category) {
        return queryFactory
            .selectFrom(product)
            .where(eqName(name),
                eqCategory(category))
            .fetch();
    }

    private BooleanExpression eqName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return product.name.contains(name);
    }

    private BooleanExpression eqCategory(String category) {
        if (StringUtils.isEmpty(category)) {
            return null;
        }
        return product.category.eq(category);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        List<Product> products = queryFactory
            .selectFrom(product)
            .orderBy(product.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> count = queryFactory.select(product.count())
            .from(product);

        return PageableExecutionUtils.getPage(products, pageable, count::fetchOne);
    }
}
