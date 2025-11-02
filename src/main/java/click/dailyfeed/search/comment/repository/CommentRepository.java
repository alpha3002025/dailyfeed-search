package click.dailyfeed.search.comment.repository;

import click.dailyfeed.search.comment.document.CommentDocument;
import click.dailyfeed.search.comment.projection.PostCommentCountProjection;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends MongoRepository<CommentDocument, ObjectId> {
    // 게시글에 대한 댓글 조회
    Page<CommentDocument> findCommentsByPostPk(Long postPk, Pageable pageable);

    // 검색어로 게시글 검색
    Page<CommentDocument> findCommentsByContentLike(String keyword, Pageable pageable);

    // 여러 포스트에 대한 댓글 수를 한 번에 조회
    @Aggregation(pipeline = {
            "{ '$match': { 'post_pk': { '$in': ?0 } } }",
            "{ '$group': { '_id': '$post_pk', 'count': { '$sum': 1 } } }",
            "{ '$project': { 'postPk': '$_id', 'commentCount': '$count', '_id': 0 } }"
    })
    List<PostCommentCountProjection> countCommentsByPostPks(Set<Long> postPks);

}
