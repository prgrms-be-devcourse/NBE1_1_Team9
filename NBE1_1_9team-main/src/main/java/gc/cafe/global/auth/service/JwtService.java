package gc.cafe.global.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import gc.cafe.global.dto.TokenResponse;
import gc.cafe.domain.user.User;
import gc.cafe.domain.user.UserRepository;
import gc.cafe.global.auth.login.LoginService;
import gc.cafe.global.event.RefreshTokenEvent;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String JWT_TOKEN = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String EMAIL_CLAIM = "email";
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    private final UserRepository userRepository;
    private final LoginService loginService;
    private final ApplicationEventPublisher publisher;

    public TokenResponse toTokenResponse(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("3번 후보"));
        String accessToken = makeAccessToken(user.getEmail());
        String refreshToken = makeRefreshToken();
        publisher.publishEvent(new RefreshTokenEvent(user.getEmail(), refreshToken));
        return new TokenResponse(accessToken, refreshToken);
    }

    private String makeAccessToken(String email) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    private String makeRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader(JWT_TOKEN);
        if (header != null) {
            if (header.startsWith(BEARER)) {
                return header.replace(BEARER, "");
            }
            return null;
        }
        return null;
    }

    public Authentication getAuthentication(String headerToken) {
        String email = extractEmail(headerToken);
        if (email != null) {
            UserDetails userDetails = loginService.loadUserByUsername(email);
            return new UsernamePasswordAuthenticationToken (
                    userDetails,
                    null,
                    new NullAuthoritiesMapper().mapAuthorities(userDetails.getAuthorities())
            );
        }
        return null;
    }

    public String extractEmail(String headerToken) {
        try {
            return JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(headerToken)
                    .getClaim(EMAIL_CLAIM)
                    .asString();
        } catch (Exception e){
            return null;
        }
    }

    public boolean isTokenValid(String headerToken) {
        if (headerToken == null) {
            return false;
        }
        try {
            JWT.require(Algorithm.HMAC512(secretKey))
                    .build().verify(headerToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

}
