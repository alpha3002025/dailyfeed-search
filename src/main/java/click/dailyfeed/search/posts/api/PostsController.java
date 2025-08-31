package click.dailyfeed.search.posts.api;

import click.dailyfeed.code.domain.contents.post.dto.PostDto;
import click.dailyfeed.code.global.web.response.DailyfeedPageResponse;
import click.dailyfeed.search.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/search/posts")
@RestController
public class PostsController {
    private final PostService postService;

    @GetMapping("/")
    public DailyfeedPageResponse<PostDto.PostSearchResult> getPosts(
            @RequestParam("keyword") String keyword,
            @PageableDefault(
                    page = 0, size = 10, sort = "createAt", direction = Sort.Direction.DESC
            ) Pageable pageable
    ){
        return postService.getPostsByKeyword(keyword, pageable);
    }
}
