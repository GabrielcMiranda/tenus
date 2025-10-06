package miranda.gabriel.tenus.adapters.outbounds.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import miranda.gabriel.tenus.adapters.outbounds.entities.JpaUserEntity;
import miranda.gabriel.tenus.adapters.outbounds.entities.embedded.EmailEmbeddable;
import miranda.gabriel.tenus.adapters.outbounds.entities.embedded.PhoneEmbeddable;


public interface JpaUserRepository extends JpaRepository<JpaUserEntity, UUID>{

    Optional<JpaUserEntity> findByUsername(String username);

    Optional<JpaUserEntity> findByEmail(EmailEmbeddable email);

    boolean existsByUsername(String username);

    boolean existsByEmail(EmailEmbeddable email);

    boolean existsByPhone(PhoneEmbeddable phone);
}
