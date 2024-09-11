package gc.cafe.api.service.order.request;

import gc.cafe.api.controller.order.request.OrderProductQuantity;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class OrderCreateServiceRequest {

    private String email;
    private String address;
    private String postcode;
    private Map<Long, Integer> orderProductQuantity;


    @Builder
    private OrderCreateServiceRequest(String email, String address, String postcode, List<OrderProductQuantity> orderProductQuantity) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderProductQuantity = orderProductQuantity.stream()
            .collect(HashMap::new, (map, orderProduct) -> map.put(orderProduct.getProductId(), orderProduct.getQuantity()), HashMap::putAll);
    }
}
