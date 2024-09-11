package gc.cafe.domain.product;

import java.util.List;

public interface CustomProductRepository {

    List<Product> findProductByNameOrCategory(String name, String category);

}
