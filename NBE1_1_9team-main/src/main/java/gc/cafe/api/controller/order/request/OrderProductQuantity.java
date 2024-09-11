package gc.cafe.api.controller.order.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProductQuantity {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @Positive(message = "상품 수량은 1 이상이어야 합니다.")
    private int quantity;

    @Builder
    private OrderProductQuantity(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
