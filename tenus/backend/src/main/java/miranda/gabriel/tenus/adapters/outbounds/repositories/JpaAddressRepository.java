package miranda.gabriel.tenus.adapters.outbounds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import miranda.gabriel.tenus.adapters.outbounds.entities.JpaAddressEntity;

public interface JpaAddressRepository extends JpaRepository<JpaAddressEntity, Long> {

}
