package click.dailyfeed.search.comment.service;

import click.dailyfeed.code.domain.content.comment.dto.CommentDto;
import click.dailyfeed.code.global.web.code.ResponseSuccessCode;
import click.dailyfeed.code.global.web.response.DailyfeedPageResponse;
import click.dailyfeed.search.comment.document.CommentDocument;
import click.dailyfeed.search.comment.mapper.CommentMapper;
import click.dailyfeed.search.comment.projection.CommentLikeCountProjection;
import click.dailyfeed.search.comment.repository.CommentLikeMongoRepository;
import click.dailyfeed.search.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentLikeMongoRepository commentLikeMongoRepository;
    private final CommentMapper commentMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DailyfeedPageResponse<CommentDto.CommentSearchResult> findCommentsByPostId(Long postId, Pageable pageable) {
        // MongoDB 조회
        Page<CommentDocument> commentsByPostPk = commentRepository.findCommentsByPostPk(postId, pageable);
        // 페이지 컨텐츠 변환
        List<CommentDto.CommentSearchResult> searchResults = commentsByPostPk.getContent().stream().map(commentMapper::toSearchResult).toList();
        // Response 변환
        return DailyfeedPageResponse.<CommentDto.CommentSearchResult>builder()
                .data(commentMapper.fromMongoPage(commentsByPostPk, searchResults))
                .status(HttpStatus.OK.value())
                .result(ResponseSuccessCode.SUCCESS)
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DailyfeedPageResponse<CommentDto.CommentSearchResult> findCommentsByContentLike(String keyword, Pageable pageable) {
        // MongoDB 조회
        Page<CommentDocument> commentsByPostPk = commentRepository.findCommentsByContentLike(keyword, pageable);
        // 페이지 컨텐츠 변환
        List<CommentDto.CommentSearchResult> searchResults = commentsByPostPk.getContent().stream().map(commentMapper::toSearchResult).toList();
        // Response 변환
        return DailyfeedPageResponse.<CommentDto.CommentSearchResult>builder()
                .data(commentMapper.fromMongoPage(commentsByPostPk, searchResults))
                .status(HttpStatus.OK.value())
                .result(ResponseSuccessCode.SUCCESS)
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommentDto.CommentLikeCountStatistics> findCommentLikQueryInCount(CommentDto.CommentLikeCountBulkRequest request) {
        List<CommentLikeCountProjection> result = commentLikeMongoRepository.countLikesByCommentPkSet(request.getCommentIds());
        return result.stream()
                .map(commentLikeCountProjection -> commentMapper.toCommentLikeCountStatistics(commentLikeCountProjection))
                .toList();
    }
}
