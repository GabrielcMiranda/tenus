package miranda.gabriel.tenus.adapters.inbounds;

import java.time.LocalTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.application.usecases.UserUseCases;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCases userService;
    
    @PutMapping("/me/message-time")
    public ResponseEntity<String> updateMessageTime( @RequestBody LocalTime message_time, @AuthenticationPrincipal Jwt jwt) {
        userService.updateMessageTime(message_time, jwt.getSubject());
        return ResponseEntity.ok("Message time updated successfully.");
    }
}
