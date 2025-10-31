package miranda.gabriel.tenus.adapters.outbounds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import miranda.gabriel.tenus.adapters.outbounds.entities.JpaTaskEntity;

public interface JpaTaskRepository extends JpaRepository<JpaTaskEntity, Long> {
    
}
