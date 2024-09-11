package gc.cafe.api.service.user.response;

public class UserIdResponse {

    private Long userId;

    public UserIdResponse(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
