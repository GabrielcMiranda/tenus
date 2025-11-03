package miranda.gabriel.tenus.adapters.outbounds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import miranda.gabriel.tenus.adapters.outbounds.entities.JpaAddressEntity;

public interface JpaAddressRepository extends JpaRepository<JpaAddressEntity, Long> {

    @Query(value = """
        SELECT ST_Distance(
            ST_MakePoint(:userLongitude, :userLatitude)::geography,
            ST_MakePoint(a.longitude, a.latitude)::geography
        )
        FROM address a
        WHERE a.address_id = :addressId
        """, nativeQuery = true)
    Double calculateDistanceInMeters(
        @Param("addressId") Long addressId,
        @Param("userLatitude") Double userLatitude,
        @Param("userLongitude") Double userLongitude
    );
}
