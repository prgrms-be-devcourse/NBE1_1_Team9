package gc.cafe.api.service.product;

import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import gc.cafe.api.service.product.request.ProductUpdateServiceRequest;
import gc.cafe.api.service.product.response.ProductResponse;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

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

    private Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 id : " + id + "를 가진 상품을 찾을 수 없습니다."));
    }
}
