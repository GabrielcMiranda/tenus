package miranda.gabriel.tenus.adapters.inbounds.dto.task;

import java.time.LocalDate;
import java.time.LocalTime;


public record TaskScheduleRequestDTO(LocalDate date, LocalTime startTime, LocalTime endTime) {

}
