package click.dailyfeed.search.comments.repository;

import click.dailyfeed.search.comments.document.CommentLikeDocument;
import click.dailyfeed.search.comments.projection.CommentLikeCountProjection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Set;

public interface CommentLikeMongoRepository extends MongoRepository<CommentLikeDocument, ObjectId> {

    // 전체 좋아요 도큐먼트 수 카운트
//    @Query(value = "{}", count = true)
//    long countAllCommentLikes();

    // 특정 포스트의 좋아요 수 카운트
    @Query(value = "{ 'comment_pk': ?0 }", count = true)
    long countByCommentPk(Long commentPk);

    // 특정 회원이 누른 좋아요 수 카운트
    @Query(value = "{ 'member_id': ?0 }", count = true)
    long countByMemberId(Long memberId);

    // 여러 댓글에 대한 좋아요 수를 한 번에 조회
    @Aggregation(pipeline = {
            "{ '$match': { 'comment_pk': { '$in': ?0 } } }",
            "{ '$group': { '_id': '$comment_pk', 'count': { '$sum': 1 } } }",
            "{ '$project': { 'commentPk': '$_id', 'likeCount': '$count', '_id': 0 } }"
    })
    List<CommentLikeCountProjection> countLikesByCommentPkSet(Set<Long> commentPkSet);
}
