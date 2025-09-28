package click.dailyfeed.search.posts.api;

import click.dailyfeed.code.domain.content.post.dto.PostDto;
import click.dailyfeed.code.global.web.response.DailyfeedPageResponse;
import click.dailyfeed.search.posts.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
}
