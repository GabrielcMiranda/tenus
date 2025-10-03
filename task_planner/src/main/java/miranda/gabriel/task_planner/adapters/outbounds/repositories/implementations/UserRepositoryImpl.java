package miranda.gabriel.task_planner.adapters.outbounds.repositories.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.task_planner.adapters.mappers.MapperService;
import miranda.gabriel.task_planner.adapters.outbounds.repositories.JpaUserRepository;
import miranda.gabriel.task_planner.core.model.user.User;
import miranda.gabriel.task_planner.core.model.user.UserRepository;
import miranda.gabriel.task_planner.core.vo.Email;
import miranda.gabriel.task_planner.core.vo.Phone;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    private final MapperService mapperService;

    @Override
    public User save(User user){
        var userEntity = mapperService.userToEntity(user);
        jpaUserRepository.save(userEntity);
        return user;
    }

    @Override
    public List<User> listAll(){
        return jpaUserRepository.findAll()
            .stream()
            .map(mapperService::userToDomain)
            .toList();
    }

    @Override
    public Optional<User> findByUsername(String username){
        var userEntity = jpaUserRepository.findByUsername(username);
        return userEntity.map(mapperService::userToDomain);
    }

    @Override 
    public Optional<User> findByEmail(String email){
        var userEntity = jpaUserRepository.findByEmail(new Email(email));
        return userEntity.map(mapperService::userToDomain);
    }

    @Override
    public Optional<User> findById(UUID id){
        var userEntity = jpaUserRepository.findById(id);
        return userEntity.map(mapperService::userToDomain);
    }

    @Override
    public boolean existsByUsername(String username){
        return jpaUserRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email){
        return jpaUserRepository.existsByEmail(new Email(email));
    }

    @Override 
    public boolean existsByPhone(String phone){
        return jpaUserRepository.existsByPhone(new Phone(phone));
    }
}
