package gc.cafe.api.service.order;

import gc.cafe.IntegrationTestSupport;
import gc.cafe.api.controller.order.request.OrderProductQuantity;
import gc.cafe.api.service.order.request.OrderCreateServiceRequest;
import gc.cafe.api.service.order.response.OrderResponse;
import gc.cafe.config.AsyncTestConfig;
import gc.cafe.domain.order.Order;
import gc.cafe.domain.order.OrderRepository;
import gc.cafe.domain.orderproduct.OrderProduct;
import gc.cafe.domain.orderproduct.OrderProductRepository;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static gc.cafe.domain.order.OrderStatus.DELIVERING;
import static gc.cafe.domain.order.OrderStatus.ORDERED;
import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(classes = AsyncTestConfig.class)
@Transactional
class OrderServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;


    @DisplayName("상품을 주문한다.")
    @Test
    void createOrder() {
        //given
        Product product1 = Product.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        Product product2 = Product.builder()
            .name("스타벅스 라떼")
            .category("음료")
            .price(3000L)
            .description("에스프레소")
            .build();

        productRepository.saveAll(List.of(product1, product2));

        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProductQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build(),
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                )
            )
            .build();

        //when
        OrderResponse response = orderService.createOrder(request);

        List<Order> orders = orderRepository.findAll();

        List<OrderProduct> orderProducts = orderProductRepository.findAll();

        //then
        assertThat(orders).hasSize(1)
            .extracting("id", "email", "address.address", "address.postcode", "orderStatus")
            .containsExactlyInAnyOrder(
                tuple(orders.get(0).getId(), "test@gmail.com", "서울시 강남구", "125454", ORDERED)
            );

        assertThat(orderProducts).hasSize(2)
            .extracting("order.id", "product.id", "quantity")
            .containsExactlyInAnyOrder(
                tuple(orders.get(0).getId(), product1.getId(), 1),
                tuple(orders.get(0).getId(), product2.getId(), 2)
            );

        assertThat(response)
            .extracting("id", "email", "address", "postcode", "orderStatus")
            .containsExactlyInAnyOrder(
                response.getId(), "test@gmail.com", "서울시 강남구", "125454", ORDERED
            );

        assertThat(response.getOrderDetails())
            .hasSize(2)
            .extracting("category", "price", "quantity")
            .containsExactlyInAnyOrder(
                tuple("원두", 50000L, 1),
                tuple("음료", 3000L, 2)
            );
    }


    @DisplayName("주문 ID로 주문을 조회한다.")
    @Test
    void getOrderByOrderId() {
        //given
        Product product1 = Product.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();
        Product product2 = Product.builder()
            .name("스타벅스 라떼")
            .category("음료")
            .price(3000L)
            .description("에스프레소")
            .build();

        List<Product> products = productRepository.saveAll(List.of(product1, product2));

        Order order = Order.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .build();

        Order savedOrder = orderRepository.save(order);

        OrderProduct orderProduct1 = createOrderProduct(order, products.get(0), 1);
        OrderProduct orderProduct2 = createOrderProduct(order, products.get(1), 2);

        orderProductRepository.saveAll(List.of(orderProduct1, orderProduct2));

        //when
        OrderResponse response = orderService.getOrder(savedOrder.getId());
        //then
        assertThat(response)
            .extracting("id", "email", "address", "postcode", "orderStatus")
            .containsExactlyInAnyOrder(
                savedOrder.getId(), "test@gmail.com", "서울시 강남구", "125454", ORDERED
            );

        assertThat(response.getOrderDetails())
            .hasSize(2)
            .extracting("category", "price", "quantity")
            .containsExactlyInAnyOrder(
                tuple("원두", 50000L, 1),
                tuple("음료", 3000L, 2)
            );
    }

    @DisplayName("주문 ID를 통해 주문을 조회 할 때 해당 ID의 주문이 존재하지 않을 때 주문을 조회 할 수 없다.")
    @Test
    void getOrderByOrderIdWhenOrderIsNull() {
        //given
        Long orderId = 1L;

        //when
        //then
        assertThatThrownBy(() -> orderService.getOrder(orderId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 주문 id : " + orderId + "를 가진 주문이 존재하지 않습니다.");
    }

    @DisplayName("이메일을 통해 주문 목록을 조회한다.")
    @Test
    void getOrdersByEmail() {
        //given
        Product product1 = Product.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();
        Product product2 = Product.builder()
            .name("스타벅스 라떼")
            .category("음료")
            .price(3000L)
            .description("에스프레소")
            .build();

        List<Product> products = productRepository.saveAll(List.of(product1, product2));

        Order order1 = Order.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .build();

        Order order2 = Order.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .build();

        orderRepository.saveAll(List.of(order1, order2));

        OrderProduct orderProduct1 = createOrderProduct(order1, products.get(0), 1);
        OrderProduct orderProduct2 = createOrderProduct(order1, products.get(1), 2);
        OrderProduct orderProduct3 = createOrderProduct(order2, products.get(0), 2);
        OrderProduct orderProduct4 = createOrderProduct(order2, products.get(1), 4);

        orderProductRepository.saveAll(List.of(
            orderProduct1,
            orderProduct2,
            orderProduct3,
            orderProduct4
        ));

        //when
        List<OrderResponse> response = orderService.getOrdersByEmail("test@gmail.com");

        //then
        assertThat(response).hasSize(2)
            .extracting("email", "address", "postcode", "orderStatus")
            .containsExactlyInAnyOrder(
                tuple("test@gmail.com", "서울시 강남구", "125454", ORDERED),
                tuple("test@gmail.com", "서울시 강남구", "125454", ORDERED)
            );

        assertThat(response.get(0).getOrderDetails())
            .hasSize(2)
            .extracting("category", "price", "quantity")
            .containsExactlyInAnyOrder(
                tuple("원두", 50000L, 1),
                tuple("음료", 3000L, 2)
            );

        assertThat(response.get(1).getOrderDetails())
            .hasSize(2)
            .extracting("category", "price", "quantity")
            .containsExactlyInAnyOrder(
                tuple("원두", 50000L, 2),
                tuple("음료", 3000L, 4)
            );
    }

    @DisplayName("매일 14시가 되면 주문 상태를 변경한다.")
    @Test
    void updateOrderStatusByScheduler() {
        //given
        Product product1 = Product.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();
        Product product2 = Product.builder()
            .name("스타벅스 라떼")
            .category("음료")
            .price(3000L)
            .description("에스프레소")
            .build();

        List<Product> products = productRepository.saveAll(List.of(product1, product2));

        Order order1 = Order.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .build();

        Order order2 = Order.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .build();

        orderRepository.saveAll(List.of(order1, order2));

        OrderProduct orderProduct1 = createOrderProduct(order1, products.get(0), 1);
        OrderProduct orderProduct2 = createOrderProduct(order1, products.get(1), 2);
        OrderProduct orderProduct3 = createOrderProduct(order2, products.get(0), 2);
        OrderProduct orderProduct4 = createOrderProduct(order2, products.get(1), 4);

        orderProductRepository.saveAll(List.of(
            orderProduct1,
            orderProduct2,
            orderProduct3,
            orderProduct4
        ));

        //when
        orderService.sendOrder();

        //then
        List<Order> orders = orderRepository.findAll();

        assertThat(orders).hasSize(2)
            .extracting("orderStatus")
            .containsExactlyInAnyOrder(DELIVERING, DELIVERING);
    }


    private OrderProduct createOrderProduct(Order order, Product product, int quantity) {
        return OrderProduct.builder()
            .order(order)
            .product(product)
            .quantity(quantity)
            .build();
    }
}