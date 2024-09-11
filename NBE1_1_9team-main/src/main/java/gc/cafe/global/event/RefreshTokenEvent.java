package gc.cafe.global.event;

public record RefreshTokenEvent(
        String email,
        String refreshToken
) {
}
