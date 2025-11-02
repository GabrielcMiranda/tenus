package miranda.gabriel.tenus.adapters.inbounds.dto.task;

public record TaskResponseDTO(Long id, String name, String imageUrl, String address,String description, String date, String startTime, String endTime, String status, boolean completed) {

}
