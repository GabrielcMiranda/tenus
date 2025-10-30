package miranda.gabriel.tenus.adapters.inbounds.dto.board;

import java.util.List;

import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskSummaryDTO;

public record BoardDetailDTO(String name, String imageUri, List<TaskSummaryDTO> tasks) {
}
