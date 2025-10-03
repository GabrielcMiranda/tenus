package miranda.gabriel.task_planner.application.usecases;

import miranda.gabriel.task_planner.application.jwt.TokenResponseDTO;
import miranda.gabriel.task_planner.core.model.user.SignUpRequestDTO;
import miranda.gabriel.task_planner.core.model.user.User;
import miranda.gabriel.task_planner.core.model.user.UserRequestDTO;

public interface AuthUseCases {

    public User register(SignUpRequestDTO dto);

    public TokenResponseDTO login(UserRequestDTO dto);

}
