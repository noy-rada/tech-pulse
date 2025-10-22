package techpulse.dto.post;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String tags;
    private String authorId;
    private Instant createdAt;
    private Instant updatedAt;
    private Long likes;

}
