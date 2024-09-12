package gc.cafe.api.service.product;

import gc.cafe.IntegrationTestSupport;
import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import gc.cafe.api.service.product.request.ProductSearchServiceRequest;
import gc.cafe.api.service.product.request.ProductUpdateServiceRequest;
import gc.cafe.api.service.product.response.ProductResponse;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
class ProductServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProductTest() {
        //given
        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        //when
        ProductResponse productResponse = productService.createProduct(request);

        List<Product> products = productRepository.findAll();

        //then
        assertThat(products).hasSize(1)
            .extracting("id", "name", "category", "price", "description")
            .containsExactlyInAnyOrder(
                tuple(products.get(0).getId(), "스타벅스 원두", "원두", 50000L, "에티오피아산")
            );

        assertThat(productResponse)
            .extracting("id", "name", "category", "price", "description")
            .containsExactlyInAnyOrder(products.get(0).getId(), "스타벅스 원두", "원두", 50000L, "에티오피아산");

    }

    @DisplayName("상품 ID를 통해 상품에 대한 상세정보를 조회한다.")
    @Test
    void getProductByProductId() {
        //given
        Product product = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");

        Product savedProduct = productRepository.save(product);

        //when
        ProductResponse productResponse = productService.getProduct(savedProduct.getId());

        //then
        assertThat(productResponse)
            .extracting("id", "name", "category", "price", "description")
            .containsExactlyInAnyOrder(savedProduct.getId(), "스타벅스 원두", "원두", 50000L, "에티오피아산");

    }

    @DisplayName("상품 ID를 통해 상품에 대한 상세정보를 조회 할 때 해당 ID의 상품이 존재하지 않을 때 상품을 조회 할 수 없다.")
    @Test
    void getProductByProductIdWhenProductIsNull() {
        //given
        Long productId = 1L;

        //when
        //then
        assertThatThrownBy(() -> productService.getProduct(productId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 id : " + productId + "를 가진 상품을 찾을 수 없습니다.");

    }

    @DisplayName("상품 ID를 통해 해당 상품을 삭제 할 수 있다.")
    @Test
    void deleteProductByProductId() {
        //given=
        Product product = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");

        Product savedProduct = productRepository.save(product);

        //when
        Long deletedProductId = productService.deleteProduct(savedProduct.getId());
        List<Product> products = productRepository.findAll();

        //then
        assertThat(products).hasSize(0);
        assertThat(deletedProductId).isEqualTo(savedProduct.getId());

    }

    @DisplayName("상품 ID를 통해 해당 상품을 삭제 할 때 해당 상품이 존재하지 않으면 상품을 삭제 할 수 없다.")
    @Test
    void deleteProductByProductIdWhenProductIsNull() {
        //given
        Long productId = 1L;

        //when
        //then
        assertThatThrownBy(() -> productService.deleteProduct(productId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 id : " + productId + "를 가진 상품을 찾을 수 없습니다.");
    }

    @DisplayName("상품 ID를 통해 해당 상품의 정보를 수정 할 수 있다.")
    @Test
    void updateProductByProductId() {
        //given
        Product product = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");

        Product savedProduct = productRepository.save(product);

        ProductUpdateServiceRequest request = ProductUpdateServiceRequest.builder()
            .name("이디야 커피")
            .category("커피")
            .price(40000L)
            .description("국산")
            .build();

        //when
        ProductResponse response = productService.updateProduct(savedProduct.getId(), request);

        //then
        assertThat(response)
            .extracting("id", "name", "category", "price", "description")
            .containsExactlyInAnyOrder(savedProduct.getId(), "이디야 커피", "커피", 40000L, "국산");
    }

    @DisplayName("상품 ID를 통해 해당 상품의 정보를 수정 할 때 해당 상품이 존재하지 않으면 상품을 삭제 할 수 없다.")
    @Test
    void updateProductByProductIdWhenProductIsNull() {
        //given
        Long productId = 1L;

        ProductUpdateServiceRequest request = ProductUpdateServiceRequest.builder()
            .name("이디야 커피")
            .category("커피")
            .price(40000L)
            .description("국산")
            .build();

        //when
        //then
        assertThatThrownBy(() -> productService.updateProduct(productId, request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 id : " + productId + "를 가진 상품을 찾을 수 없습니다.");
    }

    @DisplayName("상품 이름 또는 카테고리로 상품을 조회한다")
    @Test
    void getProductByNameOrCategory() {
        //given
        Product product1 = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");
        Product product2 = createProduct("스타벅스 라떼", "음료", 3000L, "에스프레소");
        Product product3 = createProduct("스타벅스 베이글", "베이커리", 5000L, "베이글");
        Product product4 = createProduct("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치");

        productRepository.saveAll(List.of(product1, product2, product3, product4));

        ProductSearchServiceRequest request = ProductSearchServiceRequest.builder()
            .name("스타벅스")
            .category("베이커리")
            .build();

        //when
        List<ProductResponse> response = productService.getProductByNameOrCategory(request);

        //then
        assertThat(response).hasSize(2)
            .extracting("name", "category", "price", "description")
            .containsExactlyInAnyOrder(
                tuple("스타벅스 베이글", "베이커리", 5000L, "베이글"),
                tuple("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치")
            );
    }

    @DisplayName("상품 이름만 입력 받은 경우 이름으로 상품을 조회한다")
    @Test
    void getProductByNameWithoutCategory() {
        //given
        Product product1 = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");
        Product product2 = createProduct("스타벅스 라떼", "음료", 3000L, "에스프레소");
        Product product3 = createProduct("스타벅스 베이글", "베이커리", 5000L, "베이글");
        Product product4 = createProduct("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치");

        productRepository.saveAll(List.of(product1, product2, product3, product4));

        ProductSearchServiceRequest request = ProductSearchServiceRequest.builder()
            .name("스타벅스")
            .build();

        //when
        List<ProductResponse> response = productService.getProductByNameOrCategory(request);

        //then
        assertThat(response).hasSize(4)
            .extracting("name", "category", "price", "description")
            .containsExactlyInAnyOrder(
                tuple("스타벅스 베이글", "베이커리", 5000L, "베이글"),
                tuple("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치"),
                tuple("스타벅스 원두", "원두", 50000L, "에티오피아산"),
                tuple("스타벅스 라떼", "음료", 3000L, "에스프레소")
            );
    }

    @DisplayName("카테고리만 입력 받은 경우 카테고리로 상품을 조회한다.")
    @Test
    void getProductByCategoryWithoutName() {
        //given
        Product product1 = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");
        Product product2 = createProduct("스타벅스 라떼", "음료", 3000L, "에스프레소");
        Product product3 = createProduct("스타벅스 베이글", "베이커리", 5000L, "베이글");
        Product product4 = createProduct("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치");

        productRepository.saveAll(List.of(product1, product2, product3, product4));

        ProductSearchServiceRequest request = ProductSearchServiceRequest.builder()
            .category("베이커리")
            .build();

        //when
        List<ProductResponse> response = productService.getProductByNameOrCategory(request);

        //then
        assertThat(response).hasSize(2)
            .extracting("name", "category", "price", "description")
            .containsExactlyInAnyOrder(
                tuple("스타벅스 베이글", "베이커리", 5000L, "베이글"),
                tuple("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치")
            );
    }

    @DisplayName("이름 또는 카테고리의 검색 결과가 없는 경우 빈 리스트를 반환한다")
    @CsvSource({"이디야, 텀블러", "스타벅스, 텀블러", "이디야, 커피"," , 텀블러", "이디야,  "})
    @ParameterizedTest
    void getProductByNameAndCategoryIsNotFound (String name,String category) {
        //given
        Product product1 = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");
        Product product2 = createProduct("스타벅스 라떼", "음료", 3000L, "에스프레소");
        Product product3 = createProduct("스타벅스 베이글", "베이커리", 5000L, "베이글");
        Product product4 = createProduct("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치");

        productRepository.saveAll(List.of(product1, product2, product3, product4));

        ProductSearchServiceRequest request = ProductSearchServiceRequest.builder()
            .name(name)
            .category(category)
            .build();

        //when
        List<ProductResponse> response = productService.getProductByNameOrCategory(request);

        //then
        assertThat(response).isEmpty();
    }

    @DisplayName("페이지 번호를 입력 받아 상품 리스트를 조회한다.")
    @Test
    void getProducts() {
        //given
        Product product1 = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");
        Product product2 = createProduct("스타벅스 라떼", "음료", 3000L, "에스프레소");
        Product product3 = createProduct("스타벅스 베이글", "베이커리", 5000L, "베이글");
        Product product4 = createProduct("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치");

        productRepository.saveAll(List.of(product1, product2, product3, product4));

        //when
        Page<ProductResponse> response = productService.getProducts(1);

        //then
        assertThat(response.getTotalPages()).isEqualTo(1);

        assertThat(response.getTotalElements()).isEqualTo(4);

        assertThat(response.getContent()).hasSize(4)
            .extracting("name", "category", "price", "description")
            .containsExactlyInAnyOrder(
                tuple("스타벅스 원두", "원두", 50000L, "에티오피아산"),
                tuple("스타벅스 라떼", "음료", 3000L, "에스프레소"),
                tuple("스타벅스 베이글", "베이커리", 5000L, "베이글"),
                tuple("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치")
            );
    }

    @DisplayName("페이지 번호를 입력 받아 상품 리스트를 조회한다.")
    @Test
    void getProductsOutOfPage() {
        //given
        Product product1 = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");
        Product product2 = createProduct("스타벅스 라떼", "음료", 3000L, "에스프레소");
        Product product3 = createProduct("스타벅스 베이글", "베이커리", 5000L, "베이글");
        Product product4 = createProduct("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치");

        productRepository.saveAll(List.of(product1, product2, product3, product4));

        //when
        Page<ProductResponse> response = productService.getProducts(2);

        //then
        assertThat(response.getTotalPages()).isEqualTo(1);

        assertThat(response.getTotalElements()).isEqualTo(4);

        assertThat(response.getContent()).isEmpty();
    }


    private Product createProduct(String name, String category, Long price, String description) {
        return Product.builder()
            .name(name)
            .category(category)
            .price(price)
            .description(description)
            .build();
    }

}