package techpulse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import techpulse.dto.login.LoginRequest;
import techpulse.dto.login.LoginResponse;
import techpulse.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * Authenticate the user and generate JWT token
     */
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            // Authenticate username/password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Generate JWT token
            String token = jwtUtil.generateToken(loginRequest.getUsername());

            return new LoginResponse(loginRequest.getUsername(), token);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
