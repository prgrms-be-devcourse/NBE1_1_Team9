package gc.cafe.domain.product;

import gc.cafe.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class CustomProductRepositoryImplTest extends IntegrationTestSupport {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("이름만 입력된 경우 이름으로 상품을 조회한다.")
    @Test
    void findProductByNameWithoutCategory() {
        //given
        Product product1 = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");
        Product product2 = createProduct("스타벅스 라떼", "음료", 3000L, "에스프레소");
        Product product3 = createProduct("스타벅스 베이글", "베이커리", 5000L, "베이글");

        productRepository.saveAll(List.of(product1, product2, product3));

        //when
        List<Product> products = productRepository.findProductByNameOrCategory("스타벅스", "");

        //then
        assertThat(products).hasSize(3)
            .extracting("name","category","price","description")
            .containsExactlyInAnyOrder(
                tuple("스타벅스 원두", "원두", 50000L, "에티오피아산"),
                tuple("스타벅스 라떼", "음료", 3000L, "에스프레소"),
                tuple("스타벅스 베이글", "베이커리", 5000L, "베이글")
            );
    }

    @DisplayName("카테고리만 입력 받은 경우 카테고리로 상품을 조회한다.")
    @Test
    void findProductByCategoryWithoutName() {
        //given
        Product product1 = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");
        Product product2 = createProduct("스타벅스 라떼", "음료", 3000L, "에스프레소");
        Product product3 = createProduct("스타벅스 베이글", "베이커리", 5000L, "베이글");
        Product product4 = createProduct("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치");

        productRepository.saveAll(List.of(product1, product2, product3, product4));

        //when
        List<Product> products = productRepository.findProductByNameOrCategory("", "베이커리");

        //then
        assertThat(products).hasSize(2)
            .extracting("name","category","price","description")
            .containsExactlyInAnyOrder(
                tuple("스타벅스 베이글", "베이커리", 5000L, "베이글"),
                tuple("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치")
            );
    }

    @DisplayName("상품과 카테고리를 모두 입력 받은 경우 AND 연산으로 상품을 조회한다.")
    @Test
    void findProductByNameAndCategory() {
        //given
        Product product1 = createProduct("스타벅스 원두", "원두", 50000L, "에티오피아산");
        Product product2 = createProduct("스타벅스 라떼", "음료", 3000L, "에스프레소");
        Product product3 = createProduct("스타벅스 베이글", "베이커리", 5000L, "베이글");
        Product product4 = createProduct("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치");

        productRepository.saveAll(List.of(product1, product2, product3, product4));

        //when
        List<Product> products = productRepository.findProductByNameOrCategory("스타벅스", "베이커리");

        //then
        assertThat(products).hasSize(2)
            .extracting("name","category","price","description")
            .containsExactlyInAnyOrder(
                tuple("스타벅스 베이글", "베이커리", 5000L, "베이글"),
                tuple("스타벅스 샌드위치", "베이커리", 4000L, "샌드위치")
            );
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