package miranda.gabriel.tenus.adapters.outbounds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import miranda.gabriel.tenus.adapters.outbounds.entities.JpaTaskLogEntity;

public interface JpaTaskLogRepository extends JpaRepository<JpaTaskLogEntity, Long> {

    
}
