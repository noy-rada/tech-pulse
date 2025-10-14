package techpulse.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import techpulse.domain.UserToken;
import techpulse.dto.login.LoginRequest;
import techpulse.dto.login.LoginResponse;
import techpulse.repository.UserTokenRepository;
import techpulse.security.JwtUtil;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;
    private final UserTokenRepository tokenRepository;

    /**
     * Authenticate the user and generate JWT token
     */
    public LoginResponse login(LoginRequest request) {
        try {
            // Authenticate using email
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Generate token using email
            String token = jwtUtil.generateToken(request.getEmail());
            Date issuedAt = new Date();
            Date expiresAt = new Date(issuedAt.getTime() + jwtUtil.getExpirationMs());

            // Save token in DB
            UserToken userToken = UserToken.builder()
                    .token(token)
                    .username(request.getEmail()) // use email here
                    .issuedAt(String.valueOf(issuedAt))
                    .expiresAt(expiresAt)
                    .revoked(false)
                    .build();

            tokenRepository.save(userToken);

            return new LoginResponse(request.getEmail(), token, expiresAt);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    /**
     * Logout user by blacklisting token
     */
    public void logout(String token) {
        tokenRepository.findByToken(token).ifPresent(userToken -> {
            userToken.setRevoked(true);
            tokenRepository.save(userToken);
        });
    }

    public boolean isTokenValid(String token) {
        return tokenRepository.findByToken(token)
                .map(userToken -> !userToken.isRevoked() &&
                        userToken.getExpiresAt().after(new Date()))
                .orElse(false);
    }


}
