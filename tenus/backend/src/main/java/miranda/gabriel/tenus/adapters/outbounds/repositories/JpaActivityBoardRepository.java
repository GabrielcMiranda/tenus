package miranda.gabriel.tenus.adapters.outbounds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import miranda.gabriel.tenus.adapters.outbounds.entities.JpaActivityBoardEntity;

public interface JpaActivityBoardRepository extends JpaRepository<JpaActivityBoardEntity, Long> {

    @Modifying
    @Query(value = "DELETE FROM activity_board WHERE board_id = :id", nativeQuery = true)
    void deleteByBoardId(@Param("id") Long id);

}
