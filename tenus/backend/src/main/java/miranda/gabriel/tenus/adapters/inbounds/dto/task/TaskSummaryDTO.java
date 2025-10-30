package miranda.gabriel.tenus.adapters.inbounds.dto.task;

import java.time.LocalTime;

public record TaskSummaryDTO(String name, LocalTime startTime, LocalTime endTime) {
}
