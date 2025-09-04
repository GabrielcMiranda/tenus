package miranda.gabriel.task_planner.adapters.outbounds.repositories.implementations;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaUserEntity;
import miranda.gabriel.task_planner.adapters.outbounds.repositories.JpaUserRepository;
import miranda.gabriel.task_planner.core.model.user.User;
import miranda.gabriel.task_planner.core.model.user.UserRepository;
import miranda.gabriel.task_planner.utils.mappers.UserMapper;

public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    private final UserMapper userMapper;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository, UserMapper userMapper){
        this.jpaUserRepository = jpaUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user){
        var userEntity = userMapper.toEntity(user);
        jpaUserRepository.save(userEntity);
        return user;
    }

    @Override
    public List<User> listAll(){
        return jpaUserRepository.findAll()
            .stream().map(user -> userMapper.toDomain(user)).toList();
    }
}
