package miranda.gabriel.task_planner.adapters.outbounds.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaUserEntity;
import miranda.gabriel.task_planner.core.vo.Email;


public interface JpaUserRepository extends JpaRepository<JpaUserEntity, UUID>{

    Optional<JpaUserEntity> findByUsername(String username);

    Optional<JpaUserEntity> findByEmail(Email email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
