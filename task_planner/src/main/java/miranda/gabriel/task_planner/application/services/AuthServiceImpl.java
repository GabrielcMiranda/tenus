package miranda.gabriel.task_planner.application.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import miranda.gabriel.task_planner.adapters.mappers.MapperService;
import miranda.gabriel.task_planner.adapters.outbounds.repositories.implementations.UserRepositoryImpl;
import miranda.gabriel.task_planner.application.usecases.AuthUseCases;
import miranda.gabriel.task_planner.core.enums.UserRole;
import miranda.gabriel.task_planner.core.model.user.SignUpRequestDTO;
import miranda.gabriel.task_planner.core.model.user.User;
import miranda.gabriel.task_planner.core.model.user.UserRequestDTO;
import miranda.gabriel.task_planner.core.vo.Email;
import miranda.gabriel.task_planner.core.vo.Phone;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthUseCases{

    private final UserRepositoryImpl userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MapperService mapperService;

    @Transactional
    public User register(SignUpRequestDTO dto){

        validateUserRegistration(dto.username(), dto.email(), dto.phone());

        var user = new User();
        
        user.setUsername(dto.username());
        user.setEmail(new Email(dto.email()));
        user.setPhone(new Phone(dto.phone()));
        user.setPassword(bCryptPasswordEncoder.encode(dto.password()));
        user.setRole(UserRole.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setScore(0);

        return userRepository.save(user);
        
    }


    private void validateUserRegistration(String user, String email, String phone){
        var exists = userRepository.existsByUsername(user) || userRepository.existsByEmail(email)
        || userRepository.existsByPhone(phone);

        if (exists){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username, email, or password are already been used");
        }
    }

    private Optional<User> validateLogin(UserRequestDTO dto){
        var user = userRepository.findByUsername(dto.login())
            .or(() -> userRepository.findByEmail(dto.login()));
        
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
        else if (mapperService.userToEntity(user.get()).isLoginCorrect(dto.password(), bCryptPasswordEncoder)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid user or password");
        }

        return user;
    }
}
