package click.dailyfeed.search.comments.repository;

import click.dailyfeed.search.comments.document.CommentDocument;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<CommentDocument, ObjectId> {
    // 게시글에 대한 댓글 조회
    Page<CommentDocument> findCommentsByPostPk(Long postPk, Pageable pageable);

    // 검색어로 게시글 검색
    Page<CommentDocument> findCommentsByContentLike(String keyword, Pageable pageable);

}
