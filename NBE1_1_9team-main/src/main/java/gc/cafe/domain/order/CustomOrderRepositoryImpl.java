package gc.cafe.domain.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import static gc.cafe.domain.order.QOrder.order;
import static gc.cafe.domain.orderproduct.QOrderProduct.orderProduct;
import static gc.cafe.domain.product.QProduct.product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Order> findByEmail(String email) {
        return jpaQueryFactory.select(order)
                .from(order)
                .join(order.orderProducts, orderProduct)
                .fetchJoin()
                .join(orderProduct.product,product)
                .fetchJoin()
                .where(order.email.eq(email))
                .fetch();
    }

    @Override
    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        return jpaQueryFactory.select(order)
                .from(order)
                .join(order.orderProducts, orderProduct)
                .fetchJoin()
                .join(orderProduct.product, product)
                .where(order.orderStatus.eq(orderStatus))
                .fetch();
    }
}