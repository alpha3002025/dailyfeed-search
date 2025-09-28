package click.dailyfeed.search.posts.mapper;

import click.dailyfeed.code.domain.content.post.dto.PostDto;
import click.dailyfeed.code.global.web.page.DailyfeedPage;
import click.dailyfeed.search.posts.document.PostDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    default PostDto.Post toPostDto(PostDocument postDocument, PostDto.Post post){
        return PostDto.Post.builder()
                .id(postDocument.getPostPk())
                .title(postDocument.getTitle())
                .content(postDocument.getContent())
                .authorId(post.getAuthorId() != null ? post.getAuthorId() : null)
                .authorName(post.getAuthorName() != null ? post.getAuthorName() : null)
                .authorHandle(post.getAuthorHandle() != null ? post.getAuthorHandle() : null)
                .authorAvatarUrl(post.getAuthorAvatarUrl() != null ? post.getAuthorAvatarUrl() : null)
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .createdAt(postDocument.getCreatedAt())
                .updatedAt(postDocument.getUpdatedAt())
                .build();
    }

    default <T> DailyfeedPage<T> fromMongoPage(Page<PostDocument> page, List<T> content) {
        return DailyfeedPage.<T>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}
