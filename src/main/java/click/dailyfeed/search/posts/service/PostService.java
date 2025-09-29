package click.dailyfeed.search.posts.service;

import click.dailyfeed.code.domain.content.post.dto.PostDto;
import click.dailyfeed.code.global.web.code.ResponseSuccessCode;
import click.dailyfeed.code.global.web.page.DailyfeedPage;
import click.dailyfeed.code.global.web.response.DailyfeedPageResponse;
import click.dailyfeed.feign.domain.post.PostFeignHelper;
import click.dailyfeed.pagination.mapper.PageMapper;
import click.dailyfeed.search.comments.projection.PostCommentCountProjection;
import click.dailyfeed.search.comments.repository.CommentRepository;
import click.dailyfeed.search.posts.document.PostDocument;
import click.dailyfeed.search.posts.mapper.PostMapper;
import click.dailyfeed.search.posts.projection.PostLikeCountProjection;
import click.dailyfeed.search.posts.repository.PostLikeMongoRepository;
import click.dailyfeed.search.posts.repository.PostMongoRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostMongoRepository postMongoRepository;
    private final CommentRepository commentRepository;
    private final PostLikeMongoRepository postLikeMongoRepository;
    private final PostFeignHelper postFeignHelper;
    private final PostMapper postMapper;
    private final PageMapper pageMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DailyfeedPageResponse<PostDto.Post> getPostsByKeyword(String keyword, Pageable pageable, String token, HttpServletResponse response) {
        Page<PostDocument> pageResult = postMongoRepository.findPostsByContentLike(keyword, pageable);

        Set<Long> ids = pageResult.getContent().stream().map(r -> r.getPostPk()).collect(Collectors.toSet());
        PostDto.PostsBulkRequest request = PostDto.PostsBulkRequest.builder().ids(ids).build();

        Map<Long, PostDto.Post> postMap = postFeignHelper.getPostMap(request, token, response);
        List<PostDto.Post> searchResults = pageResult.getContent().stream().map(sr -> {
            return postMapper.toPostDto(sr, postMap.get(sr.getPostPk()));
        }).toList();

        DailyfeedPage<PostDto.Post> page = (DailyfeedPage<PostDto.Post>) pageMapper.fromJpaPageToDailyfeedPage(pageResult, searchResults);
        return DailyfeedPageResponse.<PostDto.Post>builder()
                .data(page)
                .status(HttpStatus.OK.value())
                .result(ResponseSuccessCode.SUCCESS)
                .build();
    }

    @Transactional(readOnly = true)
    public List<PostDto.PostLikeCountStatistics> countPostLikeCount(PostDto.PostLikeCountQueryBulkRequest request) {
        List<PostLikeCountProjection> result = postLikeMongoRepository.countLikesByPostPks(request.getPostPks());
        return result.stream().map(postMapper::toPostLikeStatistics).toList();
    }

    @Transactional(readOnly = true)
    public List<PostDto.PostCommentCountStatistics> countPostCommentCount(PostDto.PostCommentCountQueryBulkRequest request) {
        List<PostCommentCountProjection> result = commentRepository.countCommentsByPostPks(request.getPostPks());
        return result.stream().map(postMapper::toPostCommentCountStatistics).toList();
    }
}
