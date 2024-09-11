package gc.cafe.api.controller.user.request;

import gc.cafe.api.service.user.request.JoinServiceRequest;

public record JoinRequest(String email, String name, String password) {

    public JoinServiceRequest toServiceRequest() {
        return new JoinServiceRequest(email, name, password);
    }
}
