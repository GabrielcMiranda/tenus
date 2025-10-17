package miranda.gabriel.tenus.adapters.inbounds.dto;

public record TokenResponseDTO(String AccessTokenValue, Long AccessExpiresIn, String RefreshTokenValue, Long RefreshExpiresIn) {

}