package click.dailyfeed.search.comments.mapper;

import click.dailyfeed.code.domain.content.comment.dto.CommentDto;
import click.dailyfeed.code.global.web.page.DailyfeedPage;
import click.dailyfeed.search.comments.document.CommentDocument;
import click.dailyfeed.search.comments.projection.CommentLikeCountProjection;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

//    @Mapping(target = "pk", source = "comment.pk")
//    @Mapping(target = "parentPk", source = "comment.parentPk")
//    @Mapping(target = "postPk", source = "comment.postPk")
//    @Mapping(target = "content", source = "comment.content")
//    @Mapping(target = "author", source = "comment.author")
//    @Mapping(target = "path", source = "comment.path")
//    @Mapping(target = "depth", source = "comment.depth")
//    @Mapping(target = "createdAt", source = "comment.createdAt")
//    @Mapping(target = "updatedAt", source = "comment.updatedAt")
//    @Mapping(target = "replyCount", source = "comment.replyCount")
//    @Mapping(target = "totalReplies", source = "comment.totalReplies")
    CommentDto.CommentSearchResult toSearchResult(CommentDocument commentDocument);

    default CommentDto.CommentLikeCountStatistics toCommentLikeCountStatistics(CommentLikeCountProjection commentLikeCountProjection) {
        return CommentDto.CommentLikeCountStatistics.builder()
                .likeCount(commentLikeCountProjection.getLikeCount())
                .commentPk(commentLikeCountProjection.getCommentPk())
                .build();
    }


    default <T> DailyfeedPage<T> fromMongoPage(Page<CommentDocument> page, List<T> content) {
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

    default <T> DailyfeedPage<T> emptyPage(){
        return DailyfeedPage.<T>builder()
                .content(List.of())
                .page(0)
                .size(0)
                .totalElements(0)
                .totalPages(0)
                .isFirst(true)
                .isLast(true)
                .hasNext(false)
                .hasPrevious(false)
                .build();
    }
}
