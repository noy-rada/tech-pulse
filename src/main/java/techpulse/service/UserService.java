package techpulse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import techpulse.domain.User;
import techpulse.exception.ResourceNotFoundException;
import techpulse.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        logger.info("Fetching user with ID {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    public User createUser(User user) {
        logger.info("Creating user with email {}", user.getEmail());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        logger.info("Updating user ID {}", id);
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        logger.warn("Deleting user with ID {}", id);
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
