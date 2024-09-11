package gc.cafe.api.service.order;

import gc.cafe.api.service.order.request.OrderCreateServiceRequest;
import gc.cafe.api.service.order.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderCreateServiceRequest request);

    OrderResponse getOrder(Long id);

    List<OrderResponse> getOrdersByEmail(String email);
}
