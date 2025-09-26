package miranda.gabriel.task_planner.adapters.outbounds.repositories.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.task_planner.adapters.mappers.ActivityBoardMapper;
import miranda.gabriel.task_planner.adapters.mappers.UserMapper;
import miranda.gabriel.task_planner.adapters.outbounds.repositories.JpaUserRepository;
import miranda.gabriel.task_planner.core.model.user.User;
import miranda.gabriel.task_planner.core.model.user.UserRepository;
import miranda.gabriel.task_planner.core.vo.Email;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    private final UserMapper userMapper;

   private final ActivityBoardMapper boardMapper;

    @Override
    public User save(User user){
        var userEntity = userMapper.toEntity(user);
        userEntity.setBoards(boardMapper.toEntityList(user.getBoards()));
        jpaUserRepository.save(userEntity);
        return user;
    }

    @Override
    public List<User> listAll(){
        var users = jpaUserRepository.findAll()
            .stream().map(userEntity -> {
                var user = userMapper.toDomain(userEntity);
                user.setBoards(boardMapper.toDomainList(userEntity.getBoards()));
                return user;
            }).toList();
        return users;
    }

    @Override
    public Optional<User> findByUsername(String username){
        var userEntity = jpaUserRepository.findByUsername(username);
        return userEntity.map(userE -> {
                var user = userMapper.toDomain(userE);
                user.setBoards(boardMapper.toDomainList(userE.getBoards()));
                return user;
            });
    }

    //dar uma olhada nisso aqui
    @Override 
    public Optional<User> findByEmail(String email){
        var userEntity = jpaUserRepository.findByEmail(new Email(email));
        return userEntity.map(userE -> {
                var user = userMapper.toDomain(userE);
                user.setBoards(boardMapper.toDomainList(userE.getBoards()));
                return user;
            });
    }

    @Override
    public Optional<User> findById(UUID id){
        var userEntity = jpaUserRepository.findById(id);
        return userEntity.map(userE -> {
                var user = userMapper.toDomain(userE);
                user.setBoards(boardMapper.toDomainList(userE.getBoards()));
                return user;
            });
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
