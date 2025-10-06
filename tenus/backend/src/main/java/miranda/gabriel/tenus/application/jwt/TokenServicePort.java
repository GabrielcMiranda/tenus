package miranda.gabriel.tenus.application.jwt;

import org.springframework.security.oauth2.jwt.Jwt;

import miranda.gabriel.tenus.core.model.user.User;

public interface TokenServicePort {

    Long ACCESS_EXPIRATION = 36000L;
    Long REFRESH_EXPIRATION = 604800L;

    public String generateAccessToken(User user);

    public String generateRefreshToken(User user);

    public Jwt validateRefreshToken(String refreshToken);

}
