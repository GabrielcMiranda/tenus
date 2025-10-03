package miranda.gabriel.task_planner.application.jwt;

public record TokenResponseDTO(String AccessTokenValue, Long AccessExpiresIn, String RefreshTokenValue, Long RefreshExpiresIn) {

}
