package gc.cafe.api.controller.user.request;

import gc.cafe.api.service.user.request.JoinServiceRequest;

public class JoinRequest {

    private String email;
    private String name;
    private String password;

    public JoinRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public JoinServiceRequest toServiceRequest() {
        return new JoinServiceRequest(email, name, password);
    }
}
