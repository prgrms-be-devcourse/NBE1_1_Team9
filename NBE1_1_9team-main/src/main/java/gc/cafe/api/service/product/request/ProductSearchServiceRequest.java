package gc.cafe.api.service.product.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSearchServiceRequest {

    private String name;
    private String category;

    @Builder
    private ProductSearchServiceRequest(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
