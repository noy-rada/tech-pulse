package techpulse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import techpulse.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByAuthorId(String authorId, Pageable pageable);
    Page<Post> findByTitle(String title, Pageable pageable);

    // for tags search (naive)
    Page<Post> findByTagsContainingIgnoreCase(String tags, Pageable pageable);

    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
