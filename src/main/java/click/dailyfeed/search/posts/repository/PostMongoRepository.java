package click.dailyfeed.search.posts.repository;

import click.dailyfeed.search.posts.document.PostDocument;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostMongoRepository extends MongoRepository<PostDocument, ObjectId> {
    // 키워드 검색
    Page<PostDocument> findPostsByContentLike(String content, Pageable pageable);
}
