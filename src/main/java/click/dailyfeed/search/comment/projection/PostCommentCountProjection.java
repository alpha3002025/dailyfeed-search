package click.dailyfeed.search.comment.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentCountProjection {
    private Long postPk;
    private Long commentCount;
}
