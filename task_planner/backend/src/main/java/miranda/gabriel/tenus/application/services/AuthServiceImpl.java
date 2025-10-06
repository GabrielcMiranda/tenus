package miranda.gabriel.tenus.application.services;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.mappers.MapperService;
import miranda.gabriel.tenus.application.jwt.TokenResponseDTO;
import miranda.gabriel.tenus.application.jwt.TokenServicePort;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;
import miranda.gabriel.tenus.core.enums.UserRole;
import miranda.gabriel.tenus.core.model.user.SignUpRequestDTO;
import miranda.gabriel.tenus.core.model.user.User;
import miranda.gabriel.tenus.core.model.user.UserRepository;
import miranda.gabriel.tenus.core.model.user.UserRequestDTO;
import miranda.gabriel.tenus.core.vo.Email;
import miranda.gabriel.tenus.core.vo.Phone;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthUseCases{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MapperService mapperService;
    private final TokenServicePort tokenService;

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

    @Transactional
    public TokenResponseDTO login(UserRequestDTO dto){
        var user = validateLogin(dto);

        var accessToken = tokenService.generateAccessToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);

        return new TokenResponseDTO(accessToken, TokenServicePort.ACCESS_EXPIRATION, refreshToken, TokenServicePort.REFRESH_EXPIRATION);
    }


    private void validateUserRegistration(String user, String email, String phone){
        var exists = userRepository.existsByUsername(user) || userRepository.existsByEmail(email)
        || userRepository.existsByPhone(phone);

        if (exists){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username, email, or password are already been used");
        }
    }

    private User validateLogin(UserRequestDTO dto){
        var user = userRepository.findByUsername(dto.login())
            .or(() -> userRepository.findByEmail(dto.login()));
        
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
        else if (!mapperService.userToEntity(user.get()).isLoginCorrect(dto.password(), bCryptPasswordEncoder)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid user or password");
        }

        return user.get();
    }
}
