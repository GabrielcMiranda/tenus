package miranda.gabriel.tenus.core.model.activity_board;

import java.util.List;
import java.util.Optional;

public interface ActivityBoardRepository {

    ActivityBoard save(ActivityBoard activityBoard);

    List<ActivityBoard> findAll();

    Optional<ActivityBoard> findById(Long id);

    void delete(ActivityBoard activityBoard);
    
}
