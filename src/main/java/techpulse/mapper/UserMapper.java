package techpulse.mapper;

import org.springframework.stereotype.Component;
import techpulse.domain.Role;
import techpulse.domain.User;
import techpulse.dto.UserDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    // ✅ Convert Entity -> DTO
    public UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        dto.setRoles(
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
        );
        return dto;
    }

    // ✅ Convert DTO -> Entity (optional, for update or create)
    public User toEntity(UserDTO dto, Set<Role> roles) {
        if (dto == null) return null;

        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .enabled(dto.isEnabled())
                .password(dto.getPassword()) // Note: password should be encoded later
                .roles(roles)
                .build();
    }
}
