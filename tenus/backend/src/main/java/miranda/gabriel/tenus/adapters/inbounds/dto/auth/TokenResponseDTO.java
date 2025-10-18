package miranda.gabriel.tenus.adapters.inbounds.dto.auth;

public record TokenResponseDTO(String AccessTokenValue, Long AccessExpiresIn, String RefreshTokenValue, Long RefreshExpiresIn) {

}