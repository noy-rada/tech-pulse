package techpulse.service;

import org.springframework.data.domain.Pageable;
import techpulse.dto.post.PagedResponse;
import techpulse.dto.post.PostCreateRequest;
import techpulse.dto.post.PostResponse;
import techpulse.dto.post.PostUpdateRequest;

public interface PostService {

    PostResponse createPost(PostCreateRequest request);
    PostResponse getPostById(Long id);
    PagedResponse<PostResponse> listPosts(Pageable pageable);
    PagedResponse<PostResponse> searchByTitle(String title, Pageable pageable);
    PostResponse updatePost(Long id, PostUpdateRequest request);
    void deletePost(Long id);
    PostResponse likePost(Long id);

}
