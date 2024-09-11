package gc.cafe.api.controller.order.request;

import gc.cafe.api.service.order.request.OrderCreateServiceRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateRequest {

    @Email(message = "이메일 형식이어야 합니다.")
    @Size(max = 50, message = "이메일은 50자 이하여야 합니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @Size(max = 200, message = "주소는 200자 이하여야 합니다.")
    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    @Size(max = 20, message = "우편번호는 20자 이하여야 합니다.")
    @NotBlank(message = "우편번호는 필수입니다.")
    private String postcode;

    @Valid
    @NotNull(message = "주문 할 상품은 필수입니다.")
    private List<OrderProductQuantity> orderProductsQuantity;

    @Builder
    private OrderCreateRequest(String email, String address, String postcode, List<OrderProductQuantity> orderProductsQuantity) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderProductsQuantity = orderProductsQuantity;
    }

    public OrderCreateServiceRequest toServiceRequest() {
        return OrderCreateServiceRequest.builder()
            .email(email)
            .address(address)
            .postcode(postcode)
            .orderProductQuantity(orderProductsQuantity)
            .build();
    }
}
