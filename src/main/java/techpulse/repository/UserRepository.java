package techpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techpulse.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
