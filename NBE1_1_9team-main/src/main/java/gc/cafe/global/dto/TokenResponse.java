package gc.cafe.global.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
