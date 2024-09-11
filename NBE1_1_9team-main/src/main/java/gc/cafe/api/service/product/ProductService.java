package gc.cafe.api.service.product;

import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import gc.cafe.api.service.product.request.ProductUpdateServiceRequest;
import gc.cafe.api.service.product.response.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductCreateServiceRequest request);

    Long deleteProduct(Long id);

    ProductResponse updateProduct(Long id, ProductUpdateServiceRequest request);

    ProductResponse getProduct(Long id);
}
