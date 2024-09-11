package gc.cafe.api.service.order;


import gc.cafe.api.service.order.request.OrderCreateServiceRequest;
import gc.cafe.api.service.order.response.OrderResponse;
import gc.cafe.domain.order.Order;
import gc.cafe.domain.order.OrderRepository;
import gc.cafe.domain.orderproduct.OrderProduct;
import gc.cafe.domain.orderproduct.OrderProductRepository;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static gc.cafe.domain.order.OrderStatus.DELIVERING;
import static gc.cafe.domain.order.OrderStatus.ORDERED;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderProductRepository orderProductRepository;

    @Override
    public OrderResponse createOrder(OrderCreateServiceRequest request) {

        Order order = Order.builder()
            .email(request.getEmail())
            .address(request.getAddress())
            .postcode(request.getPostcode())
            .build();

        Order savedOrder = orderRepository.save(order);

        Set<Long> productIds = request.getOrderProductQuantity().keySet();
        List<Product> products = productRepository.findAllById(productIds);

        if (productIds.size() != products.size()) {
            throw new IllegalArgumentException("주문 상품 id 중 존재하지 않는 상품이 존재합니다.");
        }

        for (Product product : products) {
            OrderProduct orderProduct = OrderProduct.builder()
                .order(savedOrder)
                .product(product)
                .quantity(request.getOrderProductQuantity().get(product.getId()))
                .build();
            orderProductRepository.save(orderProduct);
        }

        return OrderResponse.of(savedOrder);
    }


    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 주문 id : " + id + "를 가진 주문이 존재하지 않습니다."));
        return OrderResponse.of(order);
    }

    @Override
    public List<OrderResponse> getOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findByEmail(email);

        return orders.stream()
            .map(OrderResponse::of)
            .toList();
    }

    @Async("threadPoolTaskExecutor")
    @Scheduled(cron = "0 0 14 * * *")
    protected void sendOrder() {
        List<Order> orders = orderRepository.findByOrderStatus(ORDERED);

        orders.forEach(order -> order.updateStatus(DELIVERING));
    }
}
