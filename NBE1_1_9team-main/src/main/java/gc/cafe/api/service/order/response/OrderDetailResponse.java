package gc.cafe.api.service.order.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDetailResponse {

    private String category;
    private Long price;
    private int quantity;

    @Builder
    private OrderDetailResponse(String category, Long price, int quantity) {
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
}
