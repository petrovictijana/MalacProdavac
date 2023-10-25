package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.server.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
