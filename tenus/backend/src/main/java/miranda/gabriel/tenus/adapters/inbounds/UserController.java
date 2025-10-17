package miranda.gabriel.tenus.adapters.inbounds;

import java.time.LocalTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.UpdateMessageTimeRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.UserProfileDTO;
import miranda.gabriel.tenus.application.usecases.UserUseCases;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCases userService;
    
    @PutMapping("/me/message-time")
    public ResponseEntity<String> updateMessageTime(@RequestBody UpdateMessageTimeRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {
        var time = LocalTime.parse(dto.messageTime());

        userService.updateMessageTime(time, jwt.getSubject());
        return ResponseEntity.ok("Message time updated successfully.");
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> profile(@AuthenticationPrincipal Jwt jwt) {
        var userProfile = userService.getUserProfile(jwt.getSubject());
        return ResponseEntity.ok(userProfile);
    }
    
}
