package gc.cafe.domain.product;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findProductByNameOrCategory(String name, String category) {
        return queryFactory
            .selectFrom(QProduct.product)
            .where(eqName(name),
                eqCategory(category))
            .fetch();
    }

    private BooleanExpression eqName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return QProduct.product.name.contains(name);
    }

    private BooleanExpression eqCategory(String category) {
        if (StringUtils.isEmpty(category)) {
            return null;
        }
        return QProduct.product.category.eq(category);
    }

}
