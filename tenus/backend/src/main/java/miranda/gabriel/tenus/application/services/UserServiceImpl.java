package miranda.gabriel.tenus.application.services;

import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.UserProfileDTO;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;
import miranda.gabriel.tenus.application.usecases.UserUseCases;
import miranda.gabriel.tenus.core.model.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserUseCases{

    private final UserRepository userRepository;
    private final AuthUseCases authService;
    
    public void updateMessageTime(LocalTime message_time, String userId) {
        var user = authService.validateUserId(userId);
        var validMessageTime = user.getBoards().stream()
            .flatMap(board -> board.getTasks().stream())
            .noneMatch(task -> task.getStartTime().isBefore(message_time));
        
        if (!validMessageTime) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message time cannot be earlier than task start times.");
        }

        user.setMessageTime(message_time);
        userRepository.save(user);

    }

    public UserProfileDTO getUserProfile(String userId) {
        var user = authService.validateUserId(userId);
        return new UserProfileDTO(
            user.getUsername(),
            user.getEmail().getValue(), 
            user.getPhone().getValue(), 
            user.getCreatedAt() , 
            user.getMessageTime(), 
            user.getScore());
    }
}
