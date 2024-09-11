package gc.cafe.api.controller.order;

import gc.cafe.ControllerTestSupport;
import gc.cafe.api.controller.order.request.OrderCreateRequest;
import gc.cafe.api.controller.order.request.OrderProductQuantity;
import gc.cafe.api.service.order.request.OrderCreateServiceRequest;
import gc.cafe.api.service.order.response.OrderDetailResponse;
import gc.cafe.api.service.order.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static gc.cafe.domain.order.OrderStatus.ORDERED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends ControllerTestSupport {


    @DisplayName("신규 주문을 생성한다.")
    @Test
    void createOrder() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();

        given(orderService.createOrder(any(OrderCreateServiceRequest.class)))
            .willReturn(
                OrderResponse.builder()
                    .id(1L)
                    .orderStatus(ORDERED)
                    .email("test@gmail.com")
                    .address("서울시 강남구")
                    .postcode("125454")
                    .orderDetails(
                        List.of(
                            OrderDetailResponse.builder()
                                .price(1000L)
                                .quantity(1)
                                .category("원두")
                                .build()
                            ,
                            OrderDetailResponse.builder()
                                .price(2000L)
                                .quantity(2)
                                .category("음료")
                                .build()
                        ))
                    .build());

        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("201"))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("CREATED"))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.orderStatus").value("ORDERED"))
            .andExpect(jsonPath("$.data.email").value("test@gmail.com"))
            .andExpect(jsonPath("$.data.address").value("서울시 강남구"))
            .andExpect(jsonPath("$.data.postcode").value("125454"))
            .andExpect(jsonPath("$.data.orderDetails").isArray())
            .andExpect(jsonPath("$.data.orderDetails[0].price").value(1000L))
            .andExpect(jsonPath("$.data.orderDetails[0].quantity").value(1))
            .andExpect(jsonPath("$.data.orderDetails[0].category").value("원두"))
            .andExpect(jsonPath("$.data.orderDetails[1].price").value(2000L))
            .andExpect(jsonPath("$.data.orderDetails[1].quantity").value(2))
            .andExpect(jsonPath("$.data.orderDetails[1].category").value("음료"));
    }

    @DisplayName("신규 주문을 생성 할 때 이메일은 이메일 형식이어야 한다.")
    @Test
    void createOrderWithEmailForm() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이메일 형식이어야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 생성 할 때 이메일은 필수값이다.")
    @Test
    void createOrderWithoutEmail() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .address("서울시 강남구")
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이메일은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 생성 할 때 이메일은 50자 이하여야 한다.")
    @Test
    void createOrderWhenEmailLengthIsOver50() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email(generateFixedLengthString(41) + "@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이메일은 50자 이하여야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 생성 할 때 이메일은 50자 이하여야 한다.")
    @Test
    void createOrderWhenEmailLengthIs0() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email(generateFixedLengthString(40) + "@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();

        given(orderService.createOrder(any(OrderCreateServiceRequest.class)))
            .willReturn(
                OrderResponse.builder()
                    .id(1L)
                    .orderStatus(ORDERED)
                    .email(generateFixedLengthString(40) + "@gmail.com")
                    .address("서울시 강남구")
                    .postcode("125454")
                    .orderDetails(
                        List.of(
                            OrderDetailResponse.builder()
                                .price(1000L)
                                .quantity(1)
                                .category("원두")
                                .build()
                            ,
                            OrderDetailResponse.builder()
                                .price(2000L)
                                .quantity(2)
                                .category("음료")
                                .build()
                        ))
                    .build());

        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("201"))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("CREATED"))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.orderStatus").value("ORDERED"))
            .andExpect(jsonPath("$.data.email").value(generateFixedLengthString(40) + "@gmail.com"))
            .andExpect(jsonPath("$.data.address").value("서울시 강남구"))
            .andExpect(jsonPath("$.data.postcode").value("125454"))
            .andExpect(jsonPath("$.data.orderDetails").isArray())
            .andExpect(jsonPath("$.data.orderDetails[0].price").value(1000L))
            .andExpect(jsonPath("$.data.orderDetails[0].quantity").value(1))
            .andExpect(jsonPath("$.data.orderDetails[0].category").value("원두"))
            .andExpect(jsonPath("$.data.orderDetails[1].price").value(2000L))
            .andExpect(jsonPath("$.data.orderDetails[1].quantity").value(2))
            .andExpect(jsonPath("$.data.orderDetails[1].category").value("음료"));
    }

    @DisplayName("신규 주문을 생성 할 때 주소는 필수값이다.")
    @Test
    void createOrderWithoutAddress() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("주소는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 생성 할 때 주소는 200자 이하여야 한다.")
    @Test
    void createOrderWhenAddressLengthIsOver200() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .address(generateFixedLengthString(201))
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("주소는 200자 이하여야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 생성 할 때 주소는 200자 이하여야 한다.")
    @Test
    void createOrderWhenAddressLengthIs200() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .address(generateFixedLengthString(200))
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();

        given(orderService.createOrder(any(OrderCreateServiceRequest.class)))
            .willReturn(
                OrderResponse.builder()
                    .id(1L)
                    .orderStatus(ORDERED)
                    .email("test@gmail.com")
                    .address(generateFixedLengthString(200))
                    .postcode("125454")
                    .orderDetails(
                        List.of(
                            OrderDetailResponse.builder()
                                .price(1000L)
                                .quantity(1)
                                .category("원두")
                                .build()
                            ,
                            OrderDetailResponse.builder()
                                .price(2000L)
                                .quantity(2)
                                .category("음료")
                                .build()
                        ))
                    .build());

        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("201"))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("CREATED"))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.orderStatus").value("ORDERED"))
            .andExpect(jsonPath("$.data.email").value("test@gmail.com"))
            .andExpect(jsonPath("$.data.address").value(generateFixedLengthString(200)))
            .andExpect(jsonPath("$.data.postcode").value("125454"))
            .andExpect(jsonPath("$.data.orderDetails").isArray())
            .andExpect(jsonPath("$.data.orderDetails[0].price").value(1000L))
            .andExpect(jsonPath("$.data.orderDetails[0].quantity").value(1))
            .andExpect(jsonPath("$.data.orderDetails[0].category").value("원두"))
            .andExpect(jsonPath("$.data.orderDetails[1].price").value(2000L))
            .andExpect(jsonPath("$.data.orderDetails[1].quantity").value(2))
            .andExpect(jsonPath("$.data.orderDetails[1].category").value("음료"));
    }

    @DisplayName("신규 주문을 생성 할 때 우편번호는 필수값이다.")
    @Test
    void createOrderWithoutPostcode() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("우편번호는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 생성 할 때 우편번호는 20자 이하여야 한다.")
    @Test
    void createOrderWhenPostcodeLengthIsOver20() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode(generateFixedLengthString(21))
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("우편번호는 20자 이하여야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 생성 할 때 우편번호는 20자 이하여야 한다.")
    @Test
    void createOrderWhenPostcodeLengthIs200() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode(generateFixedLengthString(20))
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();

        given(orderService.createOrder(any(OrderCreateServiceRequest.class)))
            .willReturn(
                OrderResponse.builder()
                    .id(1L)
                    .orderStatus(ORDERED)
                    .email("test@gmail.com")
                    .address("서울시 강남구")
                    .postcode(generateFixedLengthString(20))
                    .orderDetails(
                        List.of(
                            OrderDetailResponse.builder()
                                .price(1000L)
                                .quantity(1)
                                .category("원두")
                                .build()
                            ,
                            OrderDetailResponse.builder()
                                .price(2000L)
                                .quantity(2)
                                .category("음료")
                                .build()
                        ))
                    .build());

        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("201"))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("CREATED"))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.orderStatus").value("ORDERED"))
            .andExpect(jsonPath("$.data.email").value("test@gmail.com"))
            .andExpect(jsonPath("$.data.address").value("서울시 강남구"))
            .andExpect(jsonPath("$.data.postcode").value(generateFixedLengthString(20)))
            .andExpect(jsonPath("$.data.orderDetails").isArray())
            .andExpect(jsonPath("$.data.orderDetails[0].price").value(1000L))
            .andExpect(jsonPath("$.data.orderDetails[0].quantity").value(1))
            .andExpect(jsonPath("$.data.orderDetails[0].category").value("원두"))
            .andExpect(jsonPath("$.data.orderDetails[1].price").value(2000L))
            .andExpect(jsonPath("$.data.orderDetails[1].quantity").value(2))
            .andExpect(jsonPath("$.data.orderDetails[1].category").value("음료"));
    }

    @DisplayName("신규 주문을 생성 할 때 상품은 필수값이다.")
    @Test
    void createOrderWithoutProduct() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("주문 할 상품은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 생성 할 때 상품ID는 필수값이다.")
    @Test
    void createOrderWithoutProductId() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .quantity(1)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 ID는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 생성 할 때 수량은 필수값이다.")
    @Test
    void createOrderWithoutProductQuantity() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .build()
                ))
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 수량은 1 이상이어야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 생성 할 때 상품 수량은 양의 정수이다.")
    @Test
    void createOrderWhenProductQuantityIsZero() throws Exception {
        OrderCreateRequest request = OrderCreateRequest.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProductsQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(0)
                        .build()
                    ,
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                ))
            .build();


        mockMvc.perform(
                post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)
                    ))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 수량은 1 이상이어야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("주문ID로 주문을 조회한다.")
    @Test
    void getOrderByOrderId() throws Exception {

        Long pathValue = 1L;

        given(orderService.getOrder(pathValue))
            .willReturn(
                OrderResponse.builder()
                    .id(pathValue)
                    .orderStatus(ORDERED)
                    .email("test@gmail.com")
                    .address("서울시 강남구")
                    .postcode("125454")
                    .orderDetails(
                        List.of(
                            OrderDetailResponse.builder()
                                .price(1000L)
                                .quantity(1)
                                .category("원두")
                                .build()
                            ,
                            OrderDetailResponse.builder()
                                .price(2000L)
                                .quantity(2)
                                .category("음료")
                                .build()
                        ))
                    .build());

        mockMvc.perform(
                get("/api/v1/orders/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.orderStatus").value("ORDERED"))
            .andExpect(jsonPath("$.data.email").value("test@gmail.com"))
            .andExpect(jsonPath("$.data.address").value("서울시 강남구"))
            .andExpect(jsonPath("$.data.postcode").value("125454"))
            .andExpect(jsonPath("$.data.orderDetails").isArray())
            .andExpect(jsonPath("$.data.orderDetails[0].price").value(1000L))
            .andExpect(jsonPath("$.data.orderDetails[0].quantity").value(1))
            .andExpect(jsonPath("$.data.orderDetails[0].category").value("원두"))
            .andExpect(jsonPath("$.data.orderDetails[1].price").value(2000L))
            .andExpect(jsonPath("$.data.orderDetails[1].quantity").value(2))
            .andExpect(jsonPath("$.data.orderDetails[1].category").value("음료"));

    }

    @DisplayName("이메일을 통해 주문을 조회한다.")
    @Test
    void getOrderByEmail() throws Exception {
        //given
        String email = "test@gmail.com";

        given(orderService.getOrdersByEmail(email))
            .willReturn(List.of(
                OrderResponse.builder()
                    .id(1L)
                    .email(email)
                    .address("서울시 강남구")
                    .postcode("125454")
                    .orderStatus(ORDERED)
                    .orderDetails(
                        List.of(
                            OrderDetailResponse.builder()
                                .price(1000L)
                                .quantity(1)
                                .category("원두")
                                .build()
                            ,
                            OrderDetailResponse.builder()
                                .price(2000L)
                                .quantity(2)
                                .category("음료")
                                .build()
                        ))
                    .build()
            ));

        mockMvc.perform(
                get("/api/v1/orders")
                    .param("email", email)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].id").value(1L))
            .andExpect(jsonPath("$.data[0].orderStatus").value("ORDERED"))
            .andExpect(jsonPath("$.data[0].email").value("test@gmail.com"))
            .andExpect(jsonPath("$.data[0].address").value("서울시 강남구"))
            .andExpect(jsonPath("$.data[0].postcode").value("125454"))
            .andExpect(jsonPath("$.data[0].orderDetails").isArray())
            .andExpect(jsonPath("$.data[0].orderDetails[0].price").value(1000L))
            .andExpect(jsonPath("$.data[0].orderDetails[0].quantity").value(1))
            .andExpect(jsonPath("$.data[0].orderDetails[0].category").value("원두"))
            .andExpect(jsonPath("$.data[0].orderDetails[1].price").value(2000L))
            .andExpect(jsonPath("$.data[0].orderDetails[1].quantity").value(2))
            .andExpect(jsonPath("$.data[0].orderDetails[1].category").value("음료"));

    }


    private static String generateFixedLengthString(int length) {
        return "나".repeat(length);
    }


}