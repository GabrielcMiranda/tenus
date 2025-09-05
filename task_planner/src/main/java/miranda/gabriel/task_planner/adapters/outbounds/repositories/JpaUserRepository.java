package miranda.gabriel.task_planner.adapters.outbounds.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaUserEntity;

public interface JpaUserRepository extends JpaRepository<JpaUserEntity, UUID>{

    Optional<JpaUserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
