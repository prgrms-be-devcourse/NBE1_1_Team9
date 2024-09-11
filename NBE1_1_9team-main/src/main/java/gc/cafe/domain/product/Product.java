package gc.cafe.domain.product;

import gc.cafe.api.service.product.request.ProductUpdateServiceRequest;
import gc.cafe.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, length = 500)
    private String description;

    @Builder
    private Product(String name, String category, Long price, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public void updateProduct(ProductUpdateServiceRequest request) {
        this.name = request.getName();
        this.category = request.getCategory();
        this.price = request.getPrice();
        this.description = request.getDescription();
    }
}
