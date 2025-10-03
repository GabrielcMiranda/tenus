package miranda.gabriel.task_planner.application.jwt;

import org.springframework.security.oauth2.jwt.Jwt;

import miranda.gabriel.task_planner.core.model.user.User;

public interface TokenServicePort {

    Long ACCESS_EXPIRATION = 36000L;
    Long REFRESH_EXPIRATION = 604800L;

    public String generateAccessToken(User user);

    public String generateRefreshToken(User user);

    public Jwt validateRefreshToken(String refreshToken);

}
