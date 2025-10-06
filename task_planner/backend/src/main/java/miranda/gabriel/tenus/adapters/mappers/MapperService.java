package miranda.gabriel.tenus.adapters.mappers;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.outbounds.entities.JpaActivityBoardEntity;
import miranda.gabriel.tenus.adapters.outbounds.entities.JpaTaskEntity;
import miranda.gabriel.tenus.adapters.outbounds.entities.JpaTaskLogEntity;
import miranda.gabriel.tenus.adapters.outbounds.entities.JpaUserEntity;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoard;
import miranda.gabriel.tenus.core.model.task.Task;
import miranda.gabriel.tenus.core.model.task_log.TaskLog;
import miranda.gabriel.tenus.core.model.user.User;

@Service
@RequiredArgsConstructor
public class MapperService {

    private final UserMapper userMapper;

    private final ActivityBoardMapper activityBoardMapper;

    private final TaskMapper taskMapper;

    private final TaskLogMapper taskLogMapper;


    public User userToDomain(JpaUserEntity entity) {
        if (entity == null) return null;
        
        User user = userMapper.toDomain(entity);
        
        if (entity.getBoards() != null) {
            user.setBoards(activityBoardToDomainList(entity.getBoards()));
        }
        
        return user;
    }

    public JpaUserEntity userToEntity(User domain) {
        if (domain == null) return null;
        
        JpaUserEntity entity = userMapper.toEntity(domain);
        
        if (domain.getBoards() != null) {
            entity.setBoards(activityBoardToEntityList(domain.getBoards()));
        }
        
        return entity;
    }

    public ActivityBoard activityBoardToDomain(JpaActivityBoardEntity entity) {
        if (entity == null) return null;
        
        ActivityBoard board = activityBoardMapper.toDomain(entity);
        
        if (entity.getOwner() != null) {
            User owner = userMapper.toDomain(entity.getOwner());
            board.setOwner(owner);
        }
        
        if (entity.getTasks() != null) {
            board.setTasks(taskToDomainList(entity.getTasks()));
        }
        
        return board;
    }

    public JpaActivityBoardEntity activityBoardToEntity(ActivityBoard domain) {
        if (domain == null) return null;
        
        JpaActivityBoardEntity entity = activityBoardMapper.toEntity(domain);
        
        if (domain.getOwner() != null) {
            JpaUserEntity owner = userMapper.toEntity(domain.getOwner());
            entity.setOwner(owner);
        }
        
        if (domain.getTasks() != null) {
            entity.setTasks(taskToEntityList(domain.getTasks()));
        }
        
        return entity;
    }

    public Task taskToDomain(JpaTaskEntity entity) {
        if (entity == null) return null;
        
        Task task = taskMapper.toDomain(entity);
        
        if (entity.getBoard() != null) {
            ActivityBoard board = activityBoardMapper.toDomain(entity.getBoard());
            if (entity.getBoard().getOwner() != null) {
                User owner = userMapper.toDomain(entity.getBoard().getOwner());
                board.setOwner(owner);
            }
            task.setBoard(board);
        }
        
        if (entity.getTaskLogs() != null) {
            task.setTaskLogs(taskLogToDomainList(entity.getTaskLogs()));
        }
        
        return task;
    }

    public JpaTaskEntity taskToEntity(Task domain) {
        if (domain == null) return null;
        
        JpaTaskEntity entity = taskMapper.toEntity(domain);
        
        if (domain.getBoard() != null) {
            JpaActivityBoardEntity board = activityBoardMapper.toEntity(domain.getBoard());
            if (domain.getBoard().getOwner() != null) {
                JpaUserEntity owner = userMapper.toEntity(domain.getBoard().getOwner());
                board.setOwner(owner);
            }
            entity.setBoard(board);
        }
        
        if (domain.getTaskLogs() != null) {
            entity.setTaskLogs(taskLogToEntityList(domain.getTaskLogs()));
        }
        
        return entity;
    }

    public TaskLog taskLogToDomain(JpaTaskLogEntity entity) {
        if (entity == null) return null;
        
        TaskLog taskLog = taskLogMapper.toDomain(entity);
        
        if (entity.getTask() != null) {
            Task task = taskMapper.toDomain(entity.getTask());
            if (entity.getTask().getBoard() != null) {
                ActivityBoard board = activityBoardMapper.toDomain(entity.getTask().getBoard());
                task.setBoard(board);
            }
            taskLog.setTask(task);
        }
        
        return taskLog;
    }

    public JpaTaskLogEntity taskLogToEntity(TaskLog domain) {
        if (domain == null) return null;
        
        JpaTaskLogEntity entity = taskLogMapper.toEntity(domain);
        
        if (domain.getTask() != null) {
            JpaTaskEntity task = taskMapper.toEntity(domain.getTask());
            if (domain.getTask().getBoard() != null) {
                JpaActivityBoardEntity board = activityBoardMapper.toEntity(domain.getTask().getBoard());
                task.setBoard(board);
            }
            entity.setTask(task);
        }
        
        return entity;
    }

    public List<User> userToDomainList(List<JpaUserEntity> entities) {
        return entities.stream().map(this::userToDomain).toList();
    }

    public List<JpaUserEntity> userToEntityList(List<User> domains) {
        return domains.stream().map(this::userToEntity).toList();
    }

    public List<ActivityBoard> activityBoardToDomainList(List<JpaActivityBoardEntity> entities) {
        return entities.stream().map(this::activityBoardToDomain).toList();
    }

    public List<JpaActivityBoardEntity> activityBoardToEntityList(List<ActivityBoard> domains) {
        return domains.stream().map(this::activityBoardToEntity).toList();
    }

    public List<Task> taskToDomainList(List<JpaTaskEntity> entities) {
        return entities.stream().map(this::taskToDomain).toList();
    }

    public List<JpaTaskEntity> taskToEntityList(List<Task> domains) {
        return domains.stream().map(this::taskToEntity).toList();
    }

    public List<TaskLog> taskLogToDomainList(List<JpaTaskLogEntity> entities) {
        return entities.stream().map(this::taskLogToDomain).toList();
    }

    public List<JpaTaskLogEntity> taskLogToEntityList(List<TaskLog> domains) {
        return domains.stream().map(this::taskLogToEntity).toList();
    }
}