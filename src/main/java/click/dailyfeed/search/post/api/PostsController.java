package click.dailyfeed.search.posts.api;

import click.dailyfeed.code.domain.content.post.dto.PostDto;
import click.dailyfeed.code.global.web.code.ResponseSuccessCode;
import click.dailyfeed.code.global.web.response.DailyfeedPageResponse;
import click.dailyfeed.code.global.web.response.DailyfeedServerResponse;
import click.dailyfeed.search.posts.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/search/posts")
@RestController
public class PostsController {
    private final PostService postService;

    @GetMapping("/")
    public DailyfeedPageResponse<PostDto.Post> getPosts(
            @RequestParam("keyword") String keyword,
            @RequestHeader("Authorization") String token,
            HttpServletResponse response,
            @PageableDefault(
                    page = 0, size = 10, sort = "createAt", direction = Sort.Direction.DESC
            ) Pageable pageable
    ){
        return postService.getPostsByKeyword(keyword, pageable, token, response);
    }

    @PostMapping("/like/query/count/in")
    public DailyfeedServerResponse<List<PostDto.PostLikeCountStatistics>> getPostLikeCount(
            @RequestBody PostDto.PostLikeCountQueryBulkRequest request
    ){
        List<PostDto.PostLikeCountStatistics> data = postService.countPostLikeCount(request);
        return DailyfeedServerResponse.<List<PostDto.PostLikeCountStatistics>>builder()
                .result(ResponseSuccessCode.SUCCESS)
                .status(HttpStatus.OK.value())
                .data(data)
                .build();
    }

    @PostMapping("/comments/query/count/in")
    public DailyfeedServerResponse<List<PostDto.PostCommentCountStatistics>> getPostCommentsCount(
            @RequestBody PostDto.PostCommentCountQueryBulkRequest request
    ){
        List<PostDto.PostCommentCountStatistics> data = postService.countPostCommentCount(request);
        return DailyfeedServerResponse.<List<PostDto.PostCommentCountStatistics>>builder()
                .result(ResponseSuccessCode.SUCCESS)
                .status(HttpStatus.OK.value())
                .data(data)
                .build();
    }
}
