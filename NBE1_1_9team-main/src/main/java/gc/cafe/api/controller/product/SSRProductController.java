package gc.cafe.api.controller.product;

import gc.cafe.api.controller.product.request.ProductCreateRequest;
import gc.cafe.api.controller.product.request.ProductUpdateRequest;
import gc.cafe.api.service.product.ProductService;
import gc.cafe.api.service.product.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class SSRProductController {
    private final ProductService productService;

    //상품 목록 페이지
    @GetMapping
    public String productsListPage(Model model) {
        List<ProductResponse> productsList = productService.getAllProducts();
        model.addAttribute("productsList", productsList);
        model.addAttribute("product", ProductCreateRequest.builder().build());
        return "products/list";
    }

    //상품 등록
    @PostMapping("/register")
    public String createProduct(@Valid ProductCreateRequest request, Model model) {
        ProductResponse product = productService.createProduct(request.toServiceRequest());
        model.addAttribute("product", product);
        return "redirect:/products";
    }

    //개별 상품 페이지
    @GetMapping("/{id}")
    public String productDetailPage(@PathVariable("id") Long id, Model model) {
        ProductResponse product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "products/detail";
    }
    //상품 삭제
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    //상품 수정 페이지
    @GetMapping("/{id}/edit")
    public String productUpdatePage(@PathVariable("id") Long id, Model model) {
        ProductResponse product = productService.getProduct(id);
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        model.addAttribute("newProduct", ProductUpdateRequest.builder().build());
        return "products/update";
    }
    //상품 수정 페이지에서 상품 수정
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @Valid ProductUpdateRequest request,
                                Model model) {
        ProductResponse product = productService.updateProduct(id, request.toServiceRequest());
        model.addAttribute("id", id);
        return "redirect:/products/{id}";
    }

}
