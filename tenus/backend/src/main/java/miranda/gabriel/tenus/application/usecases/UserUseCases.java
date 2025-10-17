package miranda.gabriel.tenus.application.usecases;

import java.time.LocalTime;

import miranda.gabriel.tenus.adapters.inbounds.dto.UserProfileDTO;

public interface UserUseCases {

    public void updateMessageTime(LocalTime message_time, String userId);

    public UserProfileDTO getUserProfile(String userId);
}
