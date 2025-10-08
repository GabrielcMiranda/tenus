package miranda.gabriel.tenus.application.usecases;

import java.time.LocalTime;

public interface UserUseCases {

    public void updateMessageTime(LocalTime message_time, String userId);
}
