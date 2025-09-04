package miranda.gabriel.task_planner.utils.mappers;

import org.mapstruct.Mapper;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaActivityBoardEntity;
import miranda.gabriel.task_planner.core.model.activity_board.ActivityBoard;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ImageMapper.class, TaskMapper.class})
public interface ActivityBoardMapper {

    ActivityBoard toDomain(JpaActivityBoardEntity entity);

    JpaActivityBoardEntity toEntity(ActivityBoard domain);
}
