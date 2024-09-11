package gc.cafe.api.controller.product;

import gc.cafe.api.ApiResponse;
import gc.cafe.api.controller.product.request.ProductCreateRequest;
import gc.cafe.api.controller.product.request.ProductUpdateRequest;
import gc.cafe.api.service.product.ProductService;
import gc.cafe.api.service.product.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return ApiResponse.created(productService.createProduct(request.toServiceRequest()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Long> deleteProduct(@PathVariable Long id) {
        return ApiResponse.ok(productService.deleteProduct(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
        return ApiResponse.ok(productService.updateProduct(id, request.toServiceRequest()));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable Long id) {
        return ApiResponse.ok(productService.getProduct(id));
    }

}
