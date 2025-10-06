package miranda.gabriel.tenus.application.jwt;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.core.model.user.User;

@Component
@RequiredArgsConstructor
public class TokenServiceAdapter implements TokenServicePort{

    private final JwtDecoder jwtDecoder;

    private final JwtEncoder jwtEncoder;

    public String generateAccessToken(User user){

        var now = Instant.now();
        
        var scope = user.getRole();

        var accessClaims = JwtClaimsSet.builder()
                .issuer("tenus")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ACCESS_EXPIRATION))
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(accessClaims)).getTokenValue();
    }

    public String generateRefreshToken(User user){

        var now = Instant.now();

        var refreshClaims = JwtClaimsSet.builder()
                .issuer("tenus")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(REFRESH_EXPIRATION))
                .claim("type", "refresh")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();
    }

    public Jwt validateRefreshToken(String refreshToken){

        Jwt decodedRefreshToken;

        try{
            decodedRefreshToken = jwtDecoder.decode(refreshToken);
        } catch(JwtException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid refresh token");
        }

        var type = decodedRefreshToken.getClaimAsString("type");
        if(!"refresh".equals(type)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid token type");
        }

        return decodedRefreshToken;
    }

}
