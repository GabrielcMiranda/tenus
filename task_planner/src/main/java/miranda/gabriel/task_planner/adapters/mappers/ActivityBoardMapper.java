package miranda.gabriel.task_planner.adapters.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaActivityBoardEntity;
import miranda.gabriel.task_planner.core.model.activity_board.ActivityBoard;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface ActivityBoardMapper {

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    ActivityBoard toDomain(JpaActivityBoardEntity entity);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    JpaActivityBoardEntity toEntity(ActivityBoard domain);

    List<ActivityBoard> toDomainList(List<JpaActivityBoardEntity> entities);

    List<JpaActivityBoardEntity> toEntityList(List<ActivityBoard> domains);
}
