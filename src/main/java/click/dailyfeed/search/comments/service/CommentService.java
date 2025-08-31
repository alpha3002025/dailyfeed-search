package click.dailyfeed.search.comments.service;

import click.dailyfeed.code.domain.contents.comment.dto.CommentDto;
import click.dailyfeed.code.global.web.response.DailyfeedPageResponse;
import click.dailyfeed.search.comments.document.Comment;
import click.dailyfeed.search.comments.mapper.CommentsMapper;
import click.dailyfeed.search.comments.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final CommentsMapper commentsMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DailyfeedPageResponse<CommentDto.CommentSearchResult> findCommentsByPostId(Long postId, Pageable pageable) {
        // MongoDB 조회
        Page<Comment> commentsByPostPk = commentsRepository.findCommentsByPostPk(postId, pageable);
        // 페이지 컨텐츠 변환
        List<CommentDto.CommentSearchResult> searchResults = commentsByPostPk.getContent().stream().map(commentsMapper::toCommentSearchResult).toList();
        // Response 변환
        return DailyfeedPageResponse.<CommentDto.CommentSearchResult>builder()
                .content(commentsMapper.fromMongoCommentPage(commentsByPostPk, searchResults))
                .ok("Y")
                .reason("SUCCESS")
                .statusCode("200")
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DailyfeedPageResponse<CommentDto.CommentSearchResult> findCommentsByContentLike(String keyword, Pageable pageable) {
        // MongoDB 조회
        Page<Comment> commentsByPostPk = commentsRepository.findCommentsByContentLike(keyword, pageable);
        // 페이지 컨텐츠 변환
        List<CommentDto.CommentSearchResult> searchResults = commentsByPostPk.getContent().stream().map(commentsMapper::toCommentSearchResult).toList();
        // Response 변환
        return DailyfeedPageResponse.<CommentDto.CommentSearchResult>builder()
                .content(commentsMapper.fromMongoCommentPage(commentsByPostPk, searchResults))
                .ok("Y")
                .reason("SUCCESS")
                .statusCode("200")
                .build();
    }
}
