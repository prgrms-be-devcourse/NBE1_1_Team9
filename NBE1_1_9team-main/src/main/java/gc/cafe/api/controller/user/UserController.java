package gc.cafe.api.controller.user;

import gc.cafe.api.ApiResponse;
import gc.cafe.api.controller.user.request.JoinRequest;
import gc.cafe.api.controller.user.request.LoginRequest;
import gc.cafe.api.service.user.UserService;
import gc.cafe.api.service.user.response.EmailResponse;
import gc.cafe.api.service.user.response.UserIdResponse;
import gc.cafe.global.auth.service.JwtService;
import gc.cafe.global.dto.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/join")
    public ApiResponse<UserIdResponse> join(@Valid @RequestBody JoinRequest joinRequest) {
        return ApiResponse.created(userService.join(joinRequest.toServiceRequest()));
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        EmailResponse email = userService.login(loginRequest.toAuthenticationToken());
        return ApiResponse.ok(jwtService.toTokenResponse(email.value()));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.logout(email);
        SecurityContextHolder.clearContext();
        return ApiResponse.ok(null);
    }

    @PostMapping("/reissue")
    public ApiResponse<TokenResponse> reIssueToken(@RequestParam("refreshToken") String refreshToken) {
        EmailResponse email = userService.reIssueToken(refreshToken);
        return ApiResponse.ok(jwtService.toTokenResponse(email.value()));
    }

}
