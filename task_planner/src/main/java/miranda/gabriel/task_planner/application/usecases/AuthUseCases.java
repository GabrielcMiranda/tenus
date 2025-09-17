package miranda.gabriel.task_planner.application.usecases;

import miranda.gabriel.task_planner.core.model.user.SignUpRequestDTO;
import miranda.gabriel.task_planner.core.model.user.User;

public interface AuthUseCases {

    public User register(SignUpRequestDTO dto);

}
