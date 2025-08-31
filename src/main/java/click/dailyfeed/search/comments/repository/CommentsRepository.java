package click.dailyfeed.search.comments.repository;

import click.dailyfeed.search.comments.document.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentsRepository extends MongoRepository<Comment, ObjectId> {
    // 게시글에 대한 댓글 조회
    Page<Comment> findCommentsByPostPk(Long postPk, Pageable pageable);

    // 검색어로 게시글 검색
    Page<Comment> findCommentsByContentLike(String keyword, Pageable pageable);

}
