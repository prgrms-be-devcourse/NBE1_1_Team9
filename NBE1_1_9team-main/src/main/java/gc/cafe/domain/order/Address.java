package gc.cafe.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {
    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 20)
    private String postcode;

    @Builder
    private Address(String address, String postcode) {
        this.address = address;
        this.postcode = postcode;
    }
}
