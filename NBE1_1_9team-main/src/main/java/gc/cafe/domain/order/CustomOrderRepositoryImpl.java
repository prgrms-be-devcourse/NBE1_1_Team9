package gc.cafe.domain.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gc.cafe.domain.orderproduct.QOrderProduct;
import gc.cafe.domain.product.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Order> findByEmail(String email) {
        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QProduct product = QProduct.product;
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
        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QProduct product = QProduct.product;

        return jpaQueryFactory.select(order)
                .from(order)
                .join(order.orderProducts, orderProduct)
                .fetchJoin()
                .join(orderProduct.product, product)
                .where(order.orderStatus.eq(orderStatus))
                .fetch();
    }
}