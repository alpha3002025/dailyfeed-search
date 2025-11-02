package click.dailyfeed.search.posts.repository;

import click.dailyfeed.search.posts.document.PostDocument;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PostMongoRepository extends MongoRepository<PostDocument, ObjectId> {
    // 키워드 검색
    Page<PostDocument> findPostsByContentLike(String content, Pageable pageable);

    // 삭제되지 않은 전체 포스트 개수 카운트
    long countByIsDeletedFalse();

    // 삭제되지 않은 전체 포스트 개수 카운트
    @Query(value = "{ 'is_deleted': false }", count = true)
    long countActivePostDocuments();

}
