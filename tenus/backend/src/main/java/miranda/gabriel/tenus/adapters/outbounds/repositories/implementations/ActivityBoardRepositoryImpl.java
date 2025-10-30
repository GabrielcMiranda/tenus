package miranda.gabriel.tenus.adapters.outbounds.repositories.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.mappers.MapperService;
import miranda.gabriel.tenus.adapters.outbounds.repositories.JpaActivityBoardRepository;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoard;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoardRepository;

@Repository
@RequiredArgsConstructor
public class ActivityBoardRepositoryImpl implements ActivityBoardRepository{

    private final JpaActivityBoardRepository jpaActivityBoardRepository;
    private final MapperService mapperService;

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
}
