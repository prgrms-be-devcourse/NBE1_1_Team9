package gc.cafe.domain.order;

import java.util.List;

public interface CustomOrderRepository {
    List<Order> findByEmail(String email);

    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
