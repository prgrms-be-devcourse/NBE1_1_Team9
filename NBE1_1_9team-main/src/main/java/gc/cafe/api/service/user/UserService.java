package gc.cafe.api.service.user;

import gc.cafe.api.service.user.request.JoinServiceRequest;
import gc.cafe.api.service.user.response.EmailResponse;
import gc.cafe.api.service.user.response.UserIdResponse;
import gc.cafe.domain.user.User;
import gc.cafe.domain.user.UserRepository;
import gc.cafe.global.redis.RefreshToken;
import gc.cafe.global.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public UserIdResponse join(JoinServiceRequest joinRequest) {
        checkEmail(joinRequest);
        User user = new User(joinRequest.email(), joinRequest.password(), joinRequest.name());
        user.encodePassword(passwordEncoder);
        User savedUser = userRepository.save(user);
        return new UserIdResponse(savedUser.getId());
    }

    private void checkEmail(JoinServiceRequest joinRequest) {
        if (userRepository.existsByEmail(joinRequest.email())) {
            throw new IllegalArgumentException("해당 이메일은 이미 존재합니다.");
        }
    }

    @Transactional
    public EmailResponse login(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return new EmailResponse(authentication.getName());
    }

    @Transactional
    public EmailResponse reIssueToken(String refreshToken) {
        RefreshToken byToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("그런 토큰은 없는디요."));
        refreshTokenRepository.delete(byToken);
        return new EmailResponse(byToken.getEmail());
    }

    @Transactional
    public void logout(String email) {
        refreshTokenRepository.deleteById(email);
    }
}
