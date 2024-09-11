package gc.cafe.api.controller.product.request;

import gc.cafe.api.service.product.request.ProductSearchServiceRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSearchRequest {

    private String name;
    private String category;

    @Builder
    private ProductSearchRequest(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public ProductSearchServiceRequest toServiceRequest() {
        return ProductSearchServiceRequest.builder()
            .name(name)
            .category(category)
            .build();
    }
}
