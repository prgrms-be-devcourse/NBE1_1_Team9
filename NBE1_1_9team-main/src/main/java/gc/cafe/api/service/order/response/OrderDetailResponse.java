package gc.cafe.api.service.order.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDetailResponse {
    private String productName;
    private String category;
    private Long price;
    private int quantity;

    @Builder
    private OrderDetailResponse(String productName, String category, Long price, int quantity) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
}
