package techpulse.service.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import techpulse.domain.Post;
import techpulse.dto.post.PagedResponse;
import techpulse.dto.post.PostCreateRequest;
import techpulse.dto.post.PostResponse;
import techpulse.dto.post.PostUpdateRequest;
import techpulse.exception.ResourceNotFoundException;
import techpulse.mapper.PostMapper;
import techpulse.repository.PostRepository;
import techpulse.service.PostService;

import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostResponse createPost(PostCreateRequest request) {
        Post post = PostMapper.fromCreateRequest(request);
        post = postRepository.save(post);

        return PostMapper.toResponse(post);
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        return PostMapper.toResponse(post);
    }

    @Override
    public PagedResponse<PostResponse> listPosts(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(PostMapper::toResponse).collect(Collectors.toList()),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );
    }

    @Override
    public PagedResponse<PostResponse> searchByTitle(String title, Pageable pageable) {
        Page<Post> page = postRepository.findByTitleContainingIgnoreCase(title, pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(PostMapper::toResponse).collect(Collectors.toList()),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );
    }

    @Override
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        PostMapper.updateFromRequest(request, post);
        post = postRepository.save(post);
        return PostMapper.toResponse(post);
    }

    @Override
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    @Override
    public PostResponse likePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        post.setLikes(post.getLikes() + 1);
        post = postRepository.save(post);
        return PostMapper.toResponse(post);
    }
}
