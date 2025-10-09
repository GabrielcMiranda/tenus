package miranda.gabriel.tenus.application.usecases;

import miranda.gabriel.tenus.adapters.inbounds.dto.LoginRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.SignUpRequestDTO;
import miranda.gabriel.tenus.application.jwt.TokenResponseDTO;
import miranda.gabriel.tenus.core.model.user.User;

public interface AuthUseCases {

    public User register(SignUpRequestDTO dto);

    public TokenResponseDTO login(LoginRequestDTO dto);

    public TokenResponseDTO refresh(String refreshToken);

    public void validateUserRegistration(SignUpRequestDTO dto);

    public User validateLogin(LoginRequestDTO dto);

    public User validateUserId(String userId);
}
