package gc.cafe.global.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String title,
                            int status,
                            String message) {
    public static ErrorResponse fromStatusAndMessage(HttpStatus status, String message) {
        return new ErrorResponse(
                status.getReasonPhrase(),
                status.value(),
                message
        );
    }
}
