package gc.cafe.api.service.product;

import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import gc.cafe.api.service.product.request.ProductSearchServiceRequest;
import gc.cafe.api.service.product.request.ProductUpdateServiceRequest;
import gc.cafe.api.service.product.response.ProductResponse;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final int PAGE_SIZE = 10;

    @Override
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        Product saveProduct = productRepository.save(request.toEntity());
        return ProductResponse.of(saveProduct);
    }

    @Override
    public Long deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        return id;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateServiceRequest request) {
        Product product = getProductById(id);
        product.updateProduct(request);
        return ProductResponse.of(product);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProduct(Long id) {
        Product product = getProductById(id);
        return ProductResponse.of(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getProductByNameOrCategory(ProductSearchServiceRequest request) {
        List<Product> products = productRepository.findProductByNameOrCategory(request.getName(), request.getCategory());
        return products.stream()
            .map(ProductResponse::of)
            .toList();
    }

    @Override
    public Page<ProductResponse> getProducts(int page) {
        Pageable pageable = PageRequest.of(page-1,PAGE_SIZE);
        Page<Product> products = productRepository.findAllUsingQueryDsl(pageable);
        return products.map(ProductResponse::of);
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 id : " + id + "를 가진 상품을 찾을 수 없습니다."));
    }
}
