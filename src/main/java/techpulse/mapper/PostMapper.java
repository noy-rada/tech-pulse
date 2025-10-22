package techpulse.mapper;

import techpulse.domain.Post;
import techpulse.dto.post.PostCreateRequest;
import techpulse.dto.post.PostResponse;
import techpulse.dto.post.PostUpdateRequest;

public class PostMapper {

    public static PostResponse toResponse(Post p) {
        if (p == null) return null;
        return PostResponse.builder()
                .id(p.getId())
                .title(p.getTitle())
                .content(p.getContent())
                .tags(p.getTags())
                .authorId(p.getAuthorId())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .likes(p.getLikes())
                .build();
    }

    public static Post fromCreateRequest(PostCreateRequest req) {
        if (req == null) return null;
        return Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .tags(req.getTags())
                .authorId(req.getAuthorId())
                .build();
    }

    public static void updateFromRequest(PostUpdateRequest req, Post post) {
        if (req.getTitle() != null) post.setTitle(req.getTitle());
        if (req.getContent() != null) post.setContent(req.getContent());
        if (req.getTags() != null) post.setTags(req.getTags());
    }


}
