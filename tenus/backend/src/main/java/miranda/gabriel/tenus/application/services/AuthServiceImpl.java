package miranda.gabriel.tenus.application.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.auth.LoginRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.auth.SignUpRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.auth.TokenResponseDTO;
import miranda.gabriel.tenus.adapters.mappers.MapperService;
import miranda.gabriel.tenus.application.jwt.TokenServicePort;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;
import miranda.gabriel.tenus.core.enums.UserRole;
import miranda.gabriel.tenus.core.model.user.User;
import miranda.gabriel.tenus.core.model.user.UserRepository;
import miranda.gabriel.tenus.core.vo.Email;
import miranda.gabriel.tenus.core.vo.Phone;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthUseCases{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MapperService mapperService;
    private final TokenServicePort tokenService;

    @Transactional
    public User register(SignUpRequestDTO dto){

        validateUserRegistration(dto);

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

    public TokenResponseDTO login(LoginRequestDTO dto){
        var user = validateLogin(dto);

        var accessToken = tokenService.generateAccessToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);

        return new TokenResponseDTO(accessToken, TokenServicePort.ACCESS_EXPIRATION, refreshToken, TokenServicePort.REFRESH_EXPIRATION);
    }

    public TokenResponseDTO refresh(String refreshToken){
       var decodedRefreshToken = tokenService.validateRefreshToken(refreshToken);

        var userId = decodedRefreshToken.getSubject();
        var user = validateUserId(userId);

        var jwtAccessValue = tokenService.generateAccessToken(user);
        var jwtRefreshValue = tokenService.generateRefreshToken(user);

        return new TokenResponseDTO(jwtAccessValue, TokenServicePort.ACCESS_EXPIRATION, jwtRefreshValue, TokenServicePort.REFRESH_EXPIRATION);
    }


    public void validateUserRegistration(SignUpRequestDTO dto){
        var exists = userRepository.existsByUsername(dto.username()) || userRepository.existsByEmail(dto.email())
        || userRepository.existsByPhone(dto.phone());

        if (exists){
            throw new TenusExceptions.UserAlreadyExistsException("username, email, or password are already been used");
        }
    }

    public User validateLogin(LoginRequestDTO dto){
        var user = userRepository.findByUsername(dto.login())
            .or(() -> userRepository.findByEmail(dto.login()));
        
        if(user.isEmpty()){
            throw new TenusExceptions.UserNotFoundException("user not found");
        }
        else if (!mapperService.userToEntity(user.get()).isLoginCorrect(dto.password(), bCryptPasswordEncoder)){
            throw new TenusExceptions.InvalidCredentialsException();
        }

        return user.get();
    }

    public User validateUserId(String userId){
        var user = userRepository.findById(UUID.fromString(userId))
            .orElseThrow(() -> new TenusExceptions.UserNotFoundException("user not found"));

        return user;
    }
}
