package gc.cafe.domain.order;

import gc.cafe.IntegrationTestSupport;
import gc.cafe.domain.orderproduct.OrderProduct;
import gc.cafe.domain.orderproduct.OrderProductRepository;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static gc.cafe.domain.order.OrderStatus.ORDERED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class OrderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @DisplayName("이메일을 통해 주문 목록을 조회한다.")
    @Test
    void findOrdersByEmail() {
        //given
        String email = "test@gmail.com";

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

        Product product3 = Product.builder()
            .name("스타벅스 베이글")
            .category("베이커리")
            .price(5000L)
            .description("베이글")
            .build();

        List<Product> products = productRepository.saveAll(List.of(product1, product2, product3));

        Order order1 = Order.builder()
            .email(email)
            .address("서울시 강남구")
            .postcode("125454")
            .build();

        Order order2 = Order.builder()
            .email(email)
            .address("서울시 강남구")
            .postcode("125454")
            .build();

        orderRepository.saveAll(List.of(order1, order2));

        OrderProduct orderProduct1 = createOrderProduct(order1, product1, 1);
        OrderProduct orderProduct2 = createOrderProduct(order1, product2, 2);
        OrderProduct orderProduct3 = createOrderProduct(order2, product2, 2);
        OrderProduct orderProduct4 = createOrderProduct(order2, product3, 4);

        orderProductRepository.saveAll(List.of(
            orderProduct1,
            orderProduct2,
            orderProduct3,
            orderProduct4
        ));

        //when
        List<Order> findOrdersByEmail = orderRepository.findByEmail(email);

        //then
        assertThat(findOrdersByEmail).hasSize(2)
            .extracting("email", "address.address", "address.postcode", "orderStatus")
            .containsExactlyInAnyOrder(
                tuple("test@gmail.com", "서울시 강남구", "125454", ORDERED),
                tuple("test@gmail.com", "서울시 강남구", "125454", ORDERED)
            );

        assertThat(findOrdersByEmail.get(0).getOrderProducts()).hasSize(2)
            .extracting("quantity")
            .contains(
                1, 2
            );

        assertThat(findOrdersByEmail.get(1).getOrderProducts()).hasSize(2)
            .extracting("quantity")
            .contains(
                2, 4
            );

    }

    @DisplayName("주문 상태로 주문 목록을 조회한다.")
    @Test
    void findOrdersByOrderStatus() {
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

        Product product3 = Product.builder()
            .name("스타벅스 베이글")
            .category("베이커리")
            .price(5000L)
            .description("베이글")
            .build();

        List<Product> products = productRepository.saveAll(List.of(product1, product2, product3));

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

        OrderProduct orderProduct1 = createOrderProduct(order1, product1, 1);
        OrderProduct orderProduct2 = createOrderProduct(order1, product2, 2);
        OrderProduct orderProduct3 = createOrderProduct(order2, product2, 2);
        OrderProduct orderProduct4 = createOrderProduct(order2, product3, 4);

        orderProductRepository.saveAll(List.of(
            orderProduct1,
            orderProduct2,
            orderProduct3,
            orderProduct4
        ));

        //when
        List<Order> findOrdersByOrderStatus = orderRepository.findByOrderStatus(ORDERED);
        //then
        assertThat(findOrdersByOrderStatus).hasSize(2)
            .extracting("email", "address.address", "address.postcode", "orderStatus")
            .containsExactlyInAnyOrder(
                tuple("test@gmail.com", "서울시 강남구", "125454", ORDERED),
                tuple("test@gmail.com", "서울시 강남구", "125454", ORDERED)
            );

        assertThat(findOrdersByOrderStatus.get(0).getOrderProducts()).hasSize(2)
            .extracting("quantity")
            .contains(
                1, 2
            );

        assertThat(findOrdersByOrderStatus.get(1).getOrderProducts()).hasSize(2)
            .extracting("quantity")
            .containsExactlyInAnyOrder(
                2, 4
            );
    }

    private OrderProduct createOrderProduct(Order order, Product product, int quantity) {
        return OrderProduct.builder()
            .order(order)
            .product(product)
            .quantity(quantity)
            .build();
    }
}