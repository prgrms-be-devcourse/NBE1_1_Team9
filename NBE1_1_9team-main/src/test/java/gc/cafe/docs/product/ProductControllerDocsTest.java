package gc.cafe.docs.product;

import gc.cafe.api.controller.product.ProductController;
import gc.cafe.api.controller.product.request.ProductCreateRequest;
import gc.cafe.api.controller.product.request.ProductUpdateRequest;
import gc.cafe.api.service.product.ProductService;
import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import gc.cafe.api.service.product.request.ProductUpdateServiceRequest;
import gc.cafe.api.service.product.response.ProductResponse;
import gc.cafe.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static gc.cafe.docs.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = mock(ProductService.class);

    @Override
    protected Object initController() {
        return new ProductController(productService);
    }

    @DisplayName("신규 상품을 등록하는 API")
    @Test
    void createProduct() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
            .willReturn(ProductResponse.builder()
                .id(1L)
                .name("스타벅스 원두")
                .category("원두")
                .price(50000L)
                .description("에티오피아산")
                .build());

        mockMvc.perform(
            post("/api/v1/products")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("product-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("상품 이름")
                        .attributes(field("constraints", "최대 20자")),
                    fieldWithPath("category").type(JsonFieldType.STRING)
                        .description("상품 카테고리")
                        .attributes(field("constraints", "최대 50자")),
                    fieldWithPath("price").type(JsonFieldType.NUMBER)
                        .description("상품 가격"),
                    fieldWithPath("description").type(JsonFieldType.STRING)
                        .description("상품 상세 설명")
                        .attributes(field("constraints", "최대 500자"))
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
                        .description("상품 ID"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("상품 이름"),
                    fieldWithPath("data.category").type(JsonFieldType.STRING)
                        .description("상품 카테고리"),
                    fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                        .description("상품 가격"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .description("상품 상세 설명")
                )
            ));
    }

    @DisplayName("상품을 삭제하는 API")
    @Test
    void deleteProduct() throws Exception {
        long id = 1L;

        given(productService.deleteProduct(id))
            .willReturn(id);

        mockMvc.perform(
                delete("/api/v1/products/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("product-delete",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("id")
                        .description("삭제할 상품 ID")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER)
                        .description("삭제된 상품 ID")
                )
            ));
    }

    @DisplayName("상품을 수정하는 API")
    @Test
    void updateProduct() throws Exception {
        long id = 1L;

        ProductUpdateRequest request = ProductUpdateRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        given(productService.updateProduct(eq(id), any(ProductUpdateServiceRequest.class)))
            .willReturn(ProductResponse.builder()
                .id(id)
                .name("이디야 커피")
                .category("커피")
                .price(40000L)
                .description("국산")
                .build());

        mockMvc.perform(
                put("/api/v1/products/{id}", id)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("product-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("상품 이름")
                        .attributes(field("constraints", "최대 20자")),
                    fieldWithPath("category").type(JsonFieldType.STRING)
                        .description("상품 카테고리")
                        .attributes(field("constraints", "최대 50자")),
                    fieldWithPath("price").type(JsonFieldType.NUMBER)
                        .description("상품 가격")
                        .attributes(field("constraints", "0 이상")),
                    fieldWithPath("description").type(JsonFieldType.STRING)
                        .description("상품 상세 설명")
                        .attributes(field("constraints", "최대 500자"))
                ),
                pathParameters(
                    parameterWithName("id")
                        .description("수정할 상품 ID")
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
                        .description("상품 ID"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("상품 이름"),
                    fieldWithPath("data.category").type(JsonFieldType.STRING)
                        .description("상품 카테고리"),
                    fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                        .description("상품 가격"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .description("상품 상세 설명")
                )
            ));
    }

    @DisplayName("상품을 단권 조회하는 API")
    @Test
    void getProduct() throws Exception {
        long id = 1L;

        given(productService.getProduct(id))
            .willReturn(ProductResponse.builder()
                .id(id)
                .name("스타벅스 원두")
                .category("원두")
                .price(50000L)
                .description("에티오피아산")
                .build());

        mockMvc.perform(
                get("/api/v1/products/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("product-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("id")
                        .description("조회할 상품 ID")
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
                        .description("상품 ID"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("상품 이름"),
                    fieldWithPath("data.category").type(JsonFieldType.STRING)
                        .description("상품 카테고리"),
                    fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                        .description("상품 가격"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .description("상품 상세 설명")
                )
            ));
    }
}
