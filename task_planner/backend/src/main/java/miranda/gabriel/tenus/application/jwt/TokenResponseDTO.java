package miranda.gabriel.tenus.application.jwt;

public record TokenResponseDTO(String AccessTokenValue, Long AccessExpiresIn, String RefreshTokenValue, Long RefreshExpiresIn) {

}
