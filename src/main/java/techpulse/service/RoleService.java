package techpulse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import techpulse.domain.Role;
import techpulse.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getRoleList() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id){
        return roleRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Role not found"));
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role roleDetails) {
        Role role = getRoleById(id);
        role.setName(roleDetails.getName());
        role.setDescription(roleDetails.getDescription());

        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        Role role = getRoleById(id);

        roleRepository.delete(role);
    }

}
