package miranda.gabriel.tenus.adapters.outbounds.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import miranda.gabriel.tenus.adapters.outbounds.entities.JpaImageEntity;

public interface JpaImageRepository extends JpaRepository<JpaImageEntity, Long> {

    Optional<JpaImageEntity> findByImageUri(String imageUri);
}
