package miranda.gabriel.tenus.adapters.outbounds.repositories.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.mappers.MapperService;
import miranda.gabriel.tenus.adapters.outbounds.entities.embedded.EmailEmbeddable;
import miranda.gabriel.tenus.adapters.outbounds.entities.embedded.PhoneEmbeddable;
import miranda.gabriel.tenus.adapters.outbounds.repositories.JpaUserRepository;
import miranda.gabriel.tenus.core.model.user.User;
import miranda.gabriel.tenus.core.model.user.UserRepository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    private final MapperService mapperService;

    @Override
    public User save(User user){
        var userEntity = mapperService.userToEntity(user);
        var savedEntity = jpaUserRepository.save(userEntity);
        return mapperService.userToDomain(savedEntity);
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
        var userEntity = jpaUserRepository.findByEmail(new EmailEmbeddable(email));
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
        return jpaUserRepository.existsByEmail(new EmailEmbeddable(email));
    }

    @Override 
    public boolean existsByPhone(String phone){
        return jpaUserRepository.existsByPhone(new PhoneEmbeddable(phone));
    }
}
