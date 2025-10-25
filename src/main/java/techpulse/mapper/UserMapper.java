package techpulse.mapper;

import org.springframework.stereotype.Component;
import techpulse.domain.Role;
import techpulse.domain.User;
import techpulse.dto.UserDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    // Convert Entity -> DTO
    public UserDTO toDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

    // Convert DTO -> Entity (optional, for update or create)
    public User toEntity(UserDTO dto, Set<Role> roles) {
        if (dto == null) return null;

        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .enabled(dto.isEnabled())
                .password(dto.getPassword()) // encode in service
                .roles(roles)
                .build();
    }
}
