package miranda.gabriel.tenus.adapters.inbounds.dto.board;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class BoardRequestDTO {

    private String name;

    private MultipartFile image;
}
