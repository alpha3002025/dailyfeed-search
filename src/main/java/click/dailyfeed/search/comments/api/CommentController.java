package click.dailyfeed.search.comments.api;

import click.dailyfeed.code.domain.contents.comment.dto.CommentDto;
import click.dailyfeed.code.global.web.response.DailyfeedPageResponse;
import click.dailyfeed.search.comments.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/search/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/posts/{postId}")
    public DailyfeedPageResponse<CommentDto.CommentSearchResult> getCommentsByPostId(
            @PathVariable("postId") Long postId,
            @PageableDefault(
                page = 0, size = 10, sort = "createAt", direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return commentService.findCommentsByPostId(postId, pageable);
    }

    @GetMapping
    public DailyfeedPageResponse<CommentDto.CommentSearchResult> getCommentsByKeyword(
            @RequestParam("keyword") String keyword,
            @PageableDefault(
                    page = 0, size = 10, sort = "createAt", direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return commentService.findCommentsByContentLike(keyword, pageable);
    }

    @GetMapping("/advanced")
    public DailyfeedPageResponse<CommentDto.CommentSearchResult> getCommentsAdvanced(){
        return null;
    }

}
