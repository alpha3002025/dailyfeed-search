package click.dailyfeed.search.posts.service;

import click.dailyfeed.code.domain.content.post.dto.PostDto;
import click.dailyfeed.code.global.web.code.ResponseSuccessCode;
import click.dailyfeed.code.global.web.page.DailyfeedPage;
import click.dailyfeed.code.global.web.response.DailyfeedPageResponse;
import click.dailyfeed.search.posts.document.Post;
import click.dailyfeed.search.posts.mapper.PostMapper;
import click.dailyfeed.search.posts.repository.PostMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostMongoRepository postMongoRepository;
    private final PostMapper postMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DailyfeedPageResponse<PostDto.PostSearchResult> getPostsByKeyword(String keyword, Pageable pageable) {
        Page<Post> pageResult = postMongoRepository.findPostsByContentLike(keyword, pageable);

        List<PostDto.PostSearchResult> searchResults = pageResult.getContent().stream().map(postMapper::toSearchResult).toList();

        DailyfeedPage<PostDto.PostSearchResult> page = postMapper.fromMongoPage(pageResult, searchResults);

        return DailyfeedPageResponse.<PostDto.PostSearchResult>builder()
                .data(page)
                .status(HttpStatus.OK.value())
                .result(ResponseSuccessCode.SUCCESS)
                .build();
    }
}
