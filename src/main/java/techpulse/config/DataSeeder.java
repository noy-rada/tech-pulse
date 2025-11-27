package techpulse.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import techpulse.domain.Role;
import techpulse.domain.User;
import techpulse.enums.RoleType;
import techpulse.repository.RoleRepository;
import techpulse.repository.UserRepository;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Seed Roles
        for(RoleType type : RoleType.values()){
            roleRepository.findByName(type.name())
                    .orElseGet(() -> roleRepository.save(new Role(type.name(), "Default role for " + type.name())));
        }

        // Seed Admin User if none
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName(RoleType.ADMIN.name()).orElseThrow();
            Role devRole = roleRepository.findByName(RoleType.DEVELOPER.name()).orElseThrow();

            userRepository.save(User.builder()
                    .username("admin")
                    .email("admin@techpulse.com")
                    .password(passwordEncoder.encode("P@ssw0rd"))
                    .roles(Set.of(adminRole))
                    .enabled(true)
                    .build());

            userRepository.save(User.builder()
                    .username("developer")
                    .email("dev@techpulse.com")
                    .password(passwordEncoder.encode("P@ssw0rd"))
                    .roles(Set.of(devRole))
                    .enabled(true)
                    .build());
        }
    }
}
