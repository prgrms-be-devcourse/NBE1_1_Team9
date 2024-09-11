package gc.cafe.docs.order;

import gc.cafe.api.controller.order.OrderController;
import gc.cafe.api.controller.order.request.OrderCreateRequest;
import gc.cafe.api.controller.order.request.OrderProductQuantity;
import gc.cafe.api.service.order.OrderService;
import gc.cafe.api.service.order.request.OrderCreateServiceRequest;
import gc.cafe.api.service.order.response.OrderDetailResponse;
import gc.cafe.api.service.order.response.OrderResponse;
import gc.cafe.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static gc.cafe.docs.RestDocsConfig.field;
import static gc.cafe.domain.order.OrderStatus.DELIVERING;
import static gc.cafe.domain.order.OrderStatus.ORDERED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerDocsTest extends RestDocsSupport {

    private final OrderService orderService = mock(OrderService.class);

    @Override
    protected Object initController() {
        return new OrderController(orderService);
    }

    @DisplayName("신규 주문을 생성하는 API")
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
            .andDo(document("order-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("주문자 이메일")
                        .attributes(field("constraints", "최대 50자"))
                        .attributes(field("format", "XXXX@gamil.com 과 같은 이메일 형식")),
                    fieldWithPath("address").type(JsonFieldType.STRING)
                        .description("주문자 주소")
                        .attributes(field("constraints", "최대 200자")),
                    fieldWithPath("postcode").type(JsonFieldType.STRING)
                        .description("주문자 우편번호")
                        .attributes(field("constraints", "최대 20자")),
                    fieldWithPath("orderProductsQuantity").type(JsonFieldType.ARRAY)
                        .description("주문 상품 목록"),
                    fieldWithPath("orderProductsQuantity[].productId").type(JsonFieldType.NUMBER)
                        .description("상품 ID"),
                    fieldWithPath("orderProductsQuantity[].quantity").type(JsonFieldType.NUMBER)
                        .description("상품 수량")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("주문 ID"),
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("주문자 이메일"),
                    fieldWithPath("data.address").type(JsonFieldType.STRING)
                        .description("주문자 주소"),
                    fieldWithPath("data.postcode").type(JsonFieldType.STRING)
                        .description("주문자 우편번호"),
                    fieldWithPath("data.orderStatus").type(JsonFieldType.STRING)
                        .description("주문 상태"),
                    fieldWithPath("data.orderDetails").type(JsonFieldType.ARRAY)
                        .description("주문 상세"),
                    fieldWithPath("data.orderDetails[].category").type(JsonFieldType.STRING)
                        .description("주문한 상품의 카테고리"),
                    fieldWithPath("data.orderDetails[].price").type(JsonFieldType.NUMBER)
                        .description("주문한 상품의 가격"),
                    fieldWithPath("data.orderDetails[].quantity").type(JsonFieldType.NUMBER)
                        .description("주문한 상품의 수량")
                )
            ));
    }

    @DisplayName("주문을 단권 조회하는 API")
    @Test
    void getOrder() throws Exception {

        Long id = 1L;

        given(orderService.getOrder(id))
            .willReturn(
                OrderResponse.builder()
                    .id(id)
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
                get("/api/v1/orders/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("order-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("id").description("주문 ID")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("주문 ID"),
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("주문자 이메일"),
                    fieldWithPath("data.address").type(JsonFieldType.STRING)
                        .description("주문자 주소"),
                    fieldWithPath("data.postcode").type(JsonFieldType.STRING)
                        .description("주문자 우편번호"),
                    fieldWithPath("data.orderStatus").type(JsonFieldType.STRING)
                        .description("주문 상태"),
                    fieldWithPath("data.orderDetails").type(JsonFieldType.ARRAY)
                        .description("주문 상세"),
                    fieldWithPath("data.orderDetails[].category").type(JsonFieldType.STRING)
                        .description("주문한 상품의 카테고리"),
                    fieldWithPath("data.orderDetails[].price").type(JsonFieldType.NUMBER)
                        .description("주문한 상품의 가격"),
                    fieldWithPath("data.orderDetails[].quantity").type(JsonFieldType.NUMBER)
                        .description("주문한 상품의 수량")
                ))
            );

    }

    @DisplayName("이메일로 주문목록을 조회하는 API")
    @Test
    void getOrdersByEmail() throws Exception {
        String email = "test@gmail.com";


        given(orderService.getOrdersByEmail(email))
            .willReturn(List.of(
                OrderResponse.builder()
                    .id(1L)
                    .email(email)
                    .address("서울시 강남구")
                    .postcode("125454")
                    .orderStatus(DELIVERING)
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
                ,
                OrderResponse.builder()
                    .id(2L)
                    .email(email)
                    .address("서울시 강남구")
                    .postcode("125454")
                    .orderStatus(ORDERED)
                    .orderDetails(
                        List.of(
                            OrderDetailResponse.builder()
                                .price(1000L)
                                .quantity(6)
                                .category("원두")
                                .build()
                            ,
                            OrderDetailResponse.builder()
                                .price(2000L)
                                .quantity(4)
                                .category("음료")
                                .build()
                        ))
                    .build()
            ));

        mockMvc.perform(
                get("/api/v1/orders/")
                    .param("email", email)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("orders-get-by-email",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("email").description("주문자 이메일")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].id").type(JsonFieldType.NUMBER)
                        .description("주문 ID"),
                    fieldWithPath("data[].email").type(JsonFieldType.STRING)
                        .description("주문자 이메일"),
                    fieldWithPath("data[].address").type(JsonFieldType.STRING)
                        .description("주문자 주소"),
                    fieldWithPath("data[].postcode").type(JsonFieldType.STRING)
                        .description("주문자 우편번호"),
                    fieldWithPath("data[].orderStatus").type(JsonFieldType.STRING)
                        .description("주문 상태"),
                    fieldWithPath("data[].orderDetails").type(JsonFieldType.ARRAY)
                        .description("주문 상세"),
                    fieldWithPath("data[].orderDetails[].category").type(JsonFieldType.STRING)
                        .description("주문한 상품의 카테고리"),
                    fieldWithPath("data[].orderDetails[].price").type(JsonFieldType.NUMBER)
                        .description("주문한 상품의 가격"),
                    fieldWithPath("data[].orderDetails[].quantity").type(JsonFieldType.NUMBER)
                        .description("주문한 상품의 수량")
                ))
            );
    }
}
