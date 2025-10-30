package miranda.gabriel.tenus.adapters.outbounds.repositories.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miranda.gabriel.tenus.adapters.mappers.MapperService;
import miranda.gabriel.tenus.adapters.outbounds.repositories.JpaActivityBoardRepository;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoard;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoardRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ActivityBoardRepositoryImpl implements ActivityBoardRepository{

    private final JpaActivityBoardRepository jpaActivityBoardRepository;
    private final MapperService mapperService;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ActivityBoard save(ActivityBoard activityBoard) {
        var entity = mapperService.activityBoardToEntity(activityBoard);
        var savedEntity = jpaActivityBoardRepository.save(entity);
        return mapperService.activityBoardToDomain(savedEntity);
    }

    @Override
    public List<ActivityBoard> findAll() {
        var entities = jpaActivityBoardRepository.findAll();
        return mapperService.activityBoardToDomainList(entities);
    }

    @Override
    public Optional<ActivityBoard> findById(Long id) {
        var entity = jpaActivityBoardRepository.findById(id);
        return entity.map(mapperService::activityBoardToDomain);
    }

    @Override
    @Transactional
    public void delete(ActivityBoard activityBoard) {
        jpaActivityBoardRepository.deleteByBoardId(activityBoard.getId());
    }
}
