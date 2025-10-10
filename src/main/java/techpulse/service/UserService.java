package techpulse.service;

import com.sun.jdi.request.DuplicateRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import techpulse.domain.User;
import techpulse.dto.UserDTO;
import techpulse.exception.ResourceNotFoundException;
import techpulse.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // User registration
    public User registerUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .roles(Set.of("USER"))
                .enabled(true)
                .build();

        logger.info("Registered new user with username {}", user.getUsername());
        return userRepository.save(user);
    }

    // List all users
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    // Get user by id
    public User getUserById(Long id) {
        logger.info("Fetching user with ID {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    public User updateUser(Long id, UserDTO userDTO) {
        logger.info("Updating user ID {}", id);

        User user = getUserById(id);
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (userDTO.getRoles() != null) {
            user.setRoles(userDTO.getRoles());
        }

        user.setEnabled(userDTO.isEnabled());

        logger.info("Updated user with ID {}", id);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        logger.warn("Deleting user with ID {}", id);
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
