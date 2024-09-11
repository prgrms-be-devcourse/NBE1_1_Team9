package gc.cafe.api.controller.order;

import gc.cafe.api.ApiResponse;
import gc.cafe.api.controller.order.request.OrderCreateRequest;
import gc.cafe.api.service.order.OrderService;
import gc.cafe.api.service.order.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return ApiResponse.created(orderService.createOrder(request.toServiceRequest()));
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable Long id) {
        return ApiResponse.ok(orderService.getOrder(id));
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getOrdersByEmail(@RequestParam String email) {
        return ApiResponse.ok(orderService.getOrdersByEmail(email));
    }
}
