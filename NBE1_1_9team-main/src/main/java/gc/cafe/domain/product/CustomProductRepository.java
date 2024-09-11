package gc.cafe.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomProductRepository {

    List<Product> findProductByNameOrCategory(String name, String category);

    Page<Product> findAllUsingQueryDsl(Pageable pageable);
}
