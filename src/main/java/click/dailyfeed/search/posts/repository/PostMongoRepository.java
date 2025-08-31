package click.dailyfeed.search.posts.repository;

import click.dailyfeed.search.posts.document.Post;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostMongoRepository extends MongoRepository<Post, ObjectId> {
    // 키워드 검색
    Page<Post> findPostsByContentLike(String content, Pageable pageable);
}
