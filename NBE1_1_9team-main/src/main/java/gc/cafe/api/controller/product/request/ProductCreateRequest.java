package gc.cafe.api.controller.product.request;

import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @Size(max = 20, message = "상품명은 20자 이하여야 합니다.")
    @NotNull(message = "상품명은 필수입니다.")
    private String name;

    @Size(max = 50, message = "카테고리는 50자 이하여야 합니다.")
    @NotNull(message = "카테고리는 필수입니다.")
    private String category;

    @Positive(message = "가격은 양수이어야 합니다.")
    @NotNull(message = "가격은 필수입니다.")
    private Long price;

    @Size(max = 500, message = "상품 설명은 500자 이하여야 합니다.")
    @NotNull(message = "상품 설명은 필수입니다.")
    private String description;

    @Builder
    private ProductCreateRequest(String name, String category, Long price, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
            .name(name)
            .category(category)
            .price(price)
            .description(description)
            .build();
    }
}
