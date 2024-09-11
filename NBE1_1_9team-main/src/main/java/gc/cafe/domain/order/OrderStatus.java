package gc.cafe.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDERED("주문 완료"),
    DELIVERING("배송 진행 중");

    private final String text;
}
