package techpulse.dto.post;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostUpdateRequest {

    @Size(max = 200)
    private String title;

    private String content;

    private String tags;

}
