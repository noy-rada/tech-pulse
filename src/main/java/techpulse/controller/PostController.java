package techpulse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import techpulse.dto.post.PagedResponse;
import techpulse.dto.post.PostCreateRequest;
import techpulse.dto.post.PostResponse;
import techpulse.dto.post.PostUpdateRequest;
import techpulse.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Posts", description = "APIs for managing posts on TechPulse platform")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService svc;

    @Autowired
    public PostController(PostService svc) {
        this.svc = svc;
    }

    @Operation(
            summary = "Create a new post",
            description = "Create a post with title, content, tags, and authorId",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Post created successfully",
                            content = @Content(schema = @Schema(implementation = PostResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreateRequest req) {
        PostResponse created = svc.createPost(req);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(
            summary = "Get post by ID",
            description = "Retrieve post details using post ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post found",
                            content = @Content(schema = @Schema(implementation = PostResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Post not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(
            @Parameter(description = "ID of the post to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(svc.getPostById(id));
    }

    @Operation(
            summary = "List all posts",
            description = "Retrieve paginated list of all posts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of posts returned")
            }
    )
    @GetMapping
    public ResponseEntity<PagedResponse<PostResponse>> list(
            @Parameter(description = "Page number (default: 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size (default: 10)") @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(svc.listPosts(pageable));
    }

    @Operation(
            summary = "Search posts by title",
            description = "Find posts by keyword in title",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Search results returned")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<PagedResponse<PostResponse>> search(
            @Parameter(description = "Keyword to search in title") @RequestParam("q") String q,
            @Parameter(description = "Page number (default: 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size (default: 10)") @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(svc.searchByTitle(q, pageable));
    }

    @Operation(
            summary = "Update a post",
            description = "Update title, content, or tags of an existing post",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Post not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(
            @Parameter(description = "ID of post to update") @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest req) {
        return ResponseEntity.ok(svc.updatePost(id, req));
    }

    @Operation(
            summary = "Delete a post",
            description = "Delete a post by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Post not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of post to delete") @PathVariable Long id) {
        svc.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Like a post",
            description = "Increment like count for a post",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Like count incremented",
                            content = @Content(schema = @Schema(implementation = PostResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Post not found")
            }
    )
    @PostMapping("/{id}/like")
    public ResponseEntity<PostResponse> like(
            @Parameter(description = "ID of post to like") @PathVariable Long id) {
        return ResponseEntity.ok(svc.likePost(id));
    }
}
