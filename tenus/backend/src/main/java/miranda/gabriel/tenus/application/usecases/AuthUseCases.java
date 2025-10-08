package miranda.gabriel.tenus.application.usecases;

import miranda.gabriel.tenus.application.jwt.TokenResponseDTO;
import miranda.gabriel.tenus.core.model.user.SignUpRequestDTO;
import miranda.gabriel.tenus.core.model.user.User;
import miranda.gabriel.tenus.core.model.user.UserRequestDTO;

public interface AuthUseCases {

    public User register(SignUpRequestDTO dto);

    public TokenResponseDTO login(UserRequestDTO dto);

    public TokenResponseDTO refresh(String refreshToken);

    public void validateUserRegistration(SignUpRequestDTO dto);

    public User validateLogin(UserRequestDTO dto);

    public User validateUserId(String userId);
}
