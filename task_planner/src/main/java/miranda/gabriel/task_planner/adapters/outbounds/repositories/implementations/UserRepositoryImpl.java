package miranda.gabriel.task_planner.adapters.outbounds.repositories.implementations;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaUserEntity;
import miranda.gabriel.task_planner.adapters.outbounds.repositories.JpaUserRepository;
import miranda.gabriel.task_planner.core.model.user.User;
import miranda.gabriel.task_planner.core.model.user.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository){
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user){
        return user;
    }
}
