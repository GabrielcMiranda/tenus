package miranda.gabriel.task_planner.adapters.outbounds.repositories.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import miranda.gabriel.task_planner.adapters.outbounds.repositories.JpaUserRepository;
import miranda.gabriel.task_planner.core.model.user.User;
import miranda.gabriel.task_planner.core.model.user.UserRepository;
import miranda.gabriel.task_planner.utils.mappers.UserMapper;

@Repository
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
        var users = jpaUserRepository.findAll()
            .stream().map(user -> userMapper.toDomain(user)).toList();
        return users;
    }

    @Override
    public Optional<User> findByUsername(String username){
        var userEntity = jpaUserRepository.findByUsername(username);
        return userEntity.map(user -> userMapper.toDomain(user));
    }

    @Override
    public Optional<User> findById(UUID id){
        var userEntity = jpaUserRepository.findById(id);
        return userEntity.map(user -> userMapper.toDomain(user));
    }

    @Override
    public boolean existsByUsername(String username){
        return jpaUserRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email){
        return jpaUserRepository.existsByEmail(email);
    }

    @Override 
    public boolean existsByPhone(String phone){
        return jpaUserRepository.existsByPhone(phone);
    }
}
