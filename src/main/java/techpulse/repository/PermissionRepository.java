package techpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techpulse.domain.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
